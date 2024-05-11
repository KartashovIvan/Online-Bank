package org.javaacademy.onlineBank.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDtoRq {
    @NonNull
    private String telephoneNumber;
    @NonNull
    private String fio;
}
