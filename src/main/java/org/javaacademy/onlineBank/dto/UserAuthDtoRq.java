package org.javaacademy.onlineBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthDtoRq {
    private String telephoneNumber;
    private String pinCode;
}
