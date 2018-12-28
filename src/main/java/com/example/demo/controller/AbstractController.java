package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AbstractController {
    public String getUsername(Principal principal){
        return ((Authentication) principal).getPrincipal().toString();
    }
}
