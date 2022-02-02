package com.example.demo.domain;

import com.example.demo.dto.CategoryDto;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class SmallestCategory {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private SmallCategory smallCategory;

    @OneToMany(mappedBy = "smallestCategory")
    private List<Product> product = new ArrayList<>();

    public static SmallestCategory create(String title){
        SmallestCategory smallestCategory = new SmallestCategory();
        smallestCategory.setTitle(title);
        return smallestCategory;
    }

    public void joinSmallCategory(SmallCategory smallCategory) {
        this.smallCategory = smallCategory;
        smallCategory.getSmallestCategory().add(this);
    }
}
