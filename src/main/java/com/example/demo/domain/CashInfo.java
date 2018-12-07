package com.example.demo.domain;


import com.example.demo.domain.enums.PayMethod;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table
@EntityListeners(value = {AuditingEntityListener.class})
public class CashInfo {
    @GeneratedValue
    @Id
    @Column
    private Long seq;

    @Column
    private PayMethod methodTitle;

    @Column
    private String bankname;

    @Column
    private String inputter;

    @Column
    private String expireDate;

    @Column
    private boolean state;

    @JoinColumn(name = "order_id", referencedColumnName = "seq")
    @OneToOne
    private ProductOrder productOrder;
}
