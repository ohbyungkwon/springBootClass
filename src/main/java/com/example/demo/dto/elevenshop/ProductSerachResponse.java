package com.example.demo.dto.elevenshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ProductSearchResponse")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSerachResponse {
    @XmlElementWrapper(name = "Products")
    @XmlElement(name = "ElevenProduct")
    private List<ElevenProduct> products;

}
