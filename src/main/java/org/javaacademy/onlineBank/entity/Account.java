package org.javaacademy.onlineBank.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class Account {
    @NonNull
    private final String accountNumber;
    @NonNull
    private User owner;
    @NonNull
    private BigDecimal balance;
}
