package org.javaacademy.onlineBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoRq {
    private String telephoneNumber;
    private String fio;
}
