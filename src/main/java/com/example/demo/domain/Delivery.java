package com.example.demo.domain;

import com.example.demo.domain.embedded.Address;
import com.example.demo.dto.OrderDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Delivery {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @CreatedDate
    private Date createDate;

    public static Delivery create(OrderDto.Create orderDto){
        Address address = Address.builder()
                .city(orderDto.getCity())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .build();

        return Delivery.builder()
                .address(address)
                .build();
    }
}
