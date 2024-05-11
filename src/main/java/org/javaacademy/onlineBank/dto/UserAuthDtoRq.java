package org.javaacademy.onlineBank.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserAuthDtoRq {
    @NonNull
    private String telephoneNumber;
    @NonNull
    private String pinCode;
}
