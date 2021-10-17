package com.example.demo.dto.elevenshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ElevenProduct")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElevenProduct {
    @XmlElement(name = "ProductCode")
    private int productCode;

    @XmlElement(name = "ProductName")
    private String productName;

    @XmlElement(name = "ProductPrice")
    private int productPrice;

    @XmlElement(name = "ProductImage")
    private String productImage;

    @XmlElement(name = "Rating")
    private int rating;

    @XmlElement(name = "DetailPageUrl")
    private String detailPageUrl;

    @XmlElement(name = "SalePrice")
    private int salePrice;

    @XmlElement(name = "Delivery")
    private String delivery;

    @XmlElement(name = "ReviewCount")
    private int reviewCount;

    @XmlElement(name = "BuySatisfy")
    private int BuySatisfy;

    @XmlElement(name = "Benefit")
    private Benefit benefit;
}
