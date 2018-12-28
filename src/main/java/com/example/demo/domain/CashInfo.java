package com.example.demo.domain;


import com.example.demo.domain.enums.CashInfoState;
import com.example.demo.domain.enums.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
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
    private CashInfoState state;

    @JoinColumn(name = "order_id", referencedColumnName = "seq")
    @OneToOne
    private ProductOrder productOrder;
}
