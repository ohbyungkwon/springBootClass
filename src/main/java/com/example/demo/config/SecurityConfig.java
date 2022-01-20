package com.example.demo.config;

import com.example.demo.auth.OAuth2CustomUserDetailService;
import com.example.demo.handler.CustomAccessDeniedHandler;
import com.example.demo.handler.CustomAuthenticationFailureHandler;
import com.example.demo.handler.CustomAuthenticationSuccessHandler;
import com.example.demo.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final static String loginUrl = "/login";
    private final static String singUpUrl = "/user";
    private final static String authUrl = "/auth/**";
    private final static String auth2Url = "/oauth2/**";

    @Autowired
    private CustomJwtFilter jwtFilter;

    @Autowired
    private CustomExceptionFilter customExceptionFilter;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;


    @Autowired
    private OAuth2CustomUserDetailService auth2UserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public CustomUsernamePasswordFilter customUsernamePasswordFilter() throws Exception{
        CustomUsernamePasswordFilter filter = new CustomUsernamePasswordFilter();
        filter.setFilterProcessesUrl(loginUrl);
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().and()
            .httpBasic().disable()
            .formLogin().disable()
            .authorizeRequests()
            .antMatchers("/",
                    "/error",
                    "/favicon.ico",
                    "/h2-console/**").permitAll()
            .antMatchers(loginUrl, authUrl, auth2Url).permitAll()
            .antMatchers(HttpMethod.POST, singUpUrl).permitAll()
            .antMatchers(HttpMethod.GET,"/products").permitAll()
            .antMatchers(HttpMethod.POST,"/products").hasRole("ADMIN_ROLE")
            .antMatchers(HttpMethod.PUT,"/products").hasRole("ADMIN_ROLE")
            .antMatchers(HttpMethod.DELETE,"/products").hasRole("ADMIN_ROLE")
            .anyRequest().authenticated()
                .and()
            .oauth2Login()
            .authorizationEndpoint()
                .baseUri("/oauth2/authorize") //클라이언트 첫 로그인 URI
                .and()
            .redirectionEndpoint()
                .baseUri("/oauth2/code/**")
                .and()
            .userInfoEndpoint()
                .userService(auth2UserDetailService)
                .and()
            .successHandler(successHandler)
            .failureHandler(failureHandler);

    http.addFilterBefore(jwtFilter, CustomUsernamePasswordFilter.class)
        .addFilterBefore(customExceptionFilter, CustomJwtFilter.class)
        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    }
}
