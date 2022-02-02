package com.example.demo.domain.embedded;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter//값 타입은 변경 불가능해야한다(Setter X)
@Builder
@Embeddable
public class Address {
    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;
}
