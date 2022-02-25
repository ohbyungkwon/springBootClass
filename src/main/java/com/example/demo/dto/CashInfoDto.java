package com.example.demo.dto;

import com.example.demo.domain.enums.CashInfoState;
import com.example.demo.domain.enums.PayMethod;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashInfoDto {
    private String bankName;
    private String inputter;
}