package org.javaacademy.onlineBank.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {
    @NonNull
    private String id;
    @NonNull
    private String telephoneNumber;
    @NonNull
    private String fio;
}
