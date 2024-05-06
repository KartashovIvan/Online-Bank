package org.javaacademy.onlineBank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.javaacademy.onlineBank.entity.type.OperationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Operation {
    @NonNull
    private String id;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private String accountNumber;
    @NonNull
    private OperationType operationType;
    @NonNull
    private BigDecimal total;
    private String description;
}
