package com.example.demo.domain;

import com.example.demo.domain.enums.CashInfoState;
import com.example.demo.domain.enums.PayMethod;
import com.example.demo.dto.OrderDto;
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
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class CashInfo {
    @Id
    @GeneratedValue
    private Long id;

    private PayMethod payMethod;

    private String bankName;

    private String inputter;

    private String expireDate;

    private CashInfoState state;

    @JoinColumn
    @OneToOne(mappedBy = "cashInfo", fetch = FetchType.LAZY)
    private ProductOrder productOrder;

    public static CashInfo create(OrderDto.Create orderDto){
        return CashInfo.builder()
                .payMethod(orderDto.getPayMethod())
                .bankName(orderDto.getCashInfo().getBankName())
                .inputter(orderDto.getCashInfo().getInputter())
                .state(CashInfoState.SUCCESS)
                .build();
    }
}
