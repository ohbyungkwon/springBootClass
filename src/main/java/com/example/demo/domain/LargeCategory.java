package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class LargeCategory {
    @Id
    @Column
    @GeneratedValue
    private Long seq;

    @Column
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "small_category_id", referencedColumnName = "seq")
    private List<SmallCategory> smallCategory;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Product> product;
}
