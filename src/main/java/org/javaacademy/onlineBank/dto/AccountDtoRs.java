package org.javaacademy.onlineBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javaacademy.onlineBank.entity.User;

@Data
@AllArgsConstructor
public class AccountDtoRs {
    private String accountNumber;
    private User owner;
}
