package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.entity.User;
import org.javaacademy.onlineBank.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService {
    private static final String ONLINE = "online";
    private static final String TOKEN = "token";
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    public String registration(String fio, String telephoneNumber) {
        if (userRepository.findByTelephoneNumber(telephoneNumber).isPresent()) {
            throw new RuntimeException("User %s with telephone number %s is exist".formatted(fio, telephoneNumber));
        }
        String id = UUID.randomUUID().toString();
        String pinCode = generatePinCode();
        authenticationService.addRecord(id, pinCode);
        userRepository.addUser(new User(id, telephoneNumber, fio));
        return pinCode;
    }

    public String authentication(String telephoneNumber, String pinCode) {
        User user = userRepository.findByTelephoneNumber(telephoneNumber)
                .orElseThrow(() -> new RuntimeException("User with telephone number %s is not exist".formatted(telephoneNumber)));
        String id = user.getId();
        if (authenticationService.authentication(id, pinCode)) {
            return ONLINE + id + TOKEN;
        }
        throw new RuntimeException("Pin code is not correctly");
    }

    public User findUserByToken(String token) {
        String id = token.replace(ONLINE, "").replace(TOKEN, "");
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not exist"));
    }

    private String generatePinCode() {
        StringBuilder pinCode = new StringBuilder();
        Stream.generate(() -> new Random().nextInt(10)).limit(4).forEach(pinCode::append);
        return pinCode.toString();
    }
}
