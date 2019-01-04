package com.example.demo.dto.elevenshop;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Benefit")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class Benefit {
    @XmlElement(name = "Discount")
    private int discount;

    @XmlElement(name = "Bounus")
    private int bounus;

    @XmlElement(name = "Point")
    private int point;

    @XmlElement(name = "InFree")
    private int inFree;
}
