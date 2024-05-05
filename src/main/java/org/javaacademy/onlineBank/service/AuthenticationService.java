package org.javaacademy.onlineBank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlineBank.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    public void addRecord(String id, String pinCode) {
        authenticationRepository.addRecord(id, pinCode);
    }

    public boolean authentication(String id, String pinCode) {
        return authenticationRepository.authentication(id, pinCode);
    }


}
