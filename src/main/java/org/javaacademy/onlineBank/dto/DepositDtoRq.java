package org.javaacademy.onlineBank.dto;

import lombok.Data;
import lombok.NonNull;
import java.math.BigDecimal;

@Data
public class DepositDtoRq {
    @NonNull
    private String accountNumber;
    @NonNull
    private BigDecimal total;
    @NonNull
    private String description;
}
