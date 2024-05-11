package org.javaacademy.onlineBank.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferDtoRq {
    private String token;
    private BigDecimal total;
    private String description;
    private String fromAccountNumber;
    private String toAccountNumber;
}
