package org.javaacademy.onlineBank.repository;

import org.javaacademy.onlineBank.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getId(),user);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByTelephoneNumber(String telephoneNumber) {
        return users.values().stream()
                .filter(user -> user.getTelephoneNumber().equals(telephoneNumber))
                .findFirst();
    }
}
