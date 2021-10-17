package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandlerDto {
    private String resultMsg;
    private Object resultObject;
    private String contentType;
    private String token;
    private HttpStatus statusCode;
}
