package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    UNAUTHORIZATION_ROLE("UNAUTHORIZATION_ROLE"),
    NORMAL_ROLE("NORMAL_ROLE"),
    ADMIN_ROLE("ADMIN_ROLE");

    private String value;
}
