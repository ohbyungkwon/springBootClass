package com.example.demo.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  Gender {
    MAN("MAN", "1"),
    WOMAN("WOMAN","2");

    private String gubun;
    private String type;

    Gender(String gubun, String type){
        this.gubun = gubun;
        this.type = type;
    }
}
