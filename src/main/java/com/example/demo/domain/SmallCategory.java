package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class SmallCategory {
    @Id
    @Column
    @GeneratedValue
    private Long seq;

    @Column
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "smallest_category_id", referencedColumnName = "seq")
    private List<SmallestCategory> smallestCategory;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "seq")
    private List<Product> product;
}
