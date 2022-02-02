package com.example.demo.domain;

import com.example.demo.dto.CategoryDto;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class LargeCategory {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "largeCategory", cascade = CascadeType.PERSIST)
    private List<SmallCategory> smallCategory = new ArrayList<>();

    @OneToMany(mappedBy = "largeCategory")
    private List<Product> product = new ArrayList<>();

    public static LargeCategory create(String title){
        LargeCategory largeCategory = new LargeCategory();
        largeCategory.setTitle(title);
        return largeCategory;
    }
}
