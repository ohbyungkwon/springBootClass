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
public class SmallCategory {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private LargeCategory largeCategory;

    @OneToMany(mappedBy = "smallCategory", cascade = CascadeType.PERSIST)
    private List<SmallestCategory> smallestCategory = new ArrayList<>();

    @OneToMany(mappedBy = "smallCategory")
    private List<Product> product = new ArrayList<>();

    public static SmallCategory create(String title){
        SmallCategory smallCategory = new SmallCategory();
        smallCategory.setTitle(title);
        return smallCategory;
    }

    public void joinLargeCategory(LargeCategory largeCategory) {
        this.largeCategory = largeCategory;
        largeCategory.getSmallCategory().add(this);
    }
}
