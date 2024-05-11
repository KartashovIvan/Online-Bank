package org.javaacademy.onlineBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javaacademy.onlineBank.entity.type.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OperationDtoRs {
    private LocalDateTime dateTime;
    private String accountNumber;
    private OperationType operationType;
    private BigDecimal total;
    private String description;
}
