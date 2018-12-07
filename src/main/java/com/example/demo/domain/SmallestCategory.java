package com.example.demo.domain;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class SmallestCategory {
    @Id
    @Column
    @GeneratedValue
    private Long seq;

    @Column
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "seq")
    private List<Product> product;
}
