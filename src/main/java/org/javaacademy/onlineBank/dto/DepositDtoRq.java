package org.javaacademy.onlineBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DepositDtoRq {
    private String accountNumber;
    private BigDecimal total;
    private String description;
}
