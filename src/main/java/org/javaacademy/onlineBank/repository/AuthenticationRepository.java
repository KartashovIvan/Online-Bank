package org.javaacademy.onlineBank.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationRepository {
    private final Map<String, String> data = new HashMap<>();

    public void addRecord(String id, String pinCode) {
        data.put(id, pinCode);
    }

    public boolean authentication(String id, String pinCode) {
        if (data.containsKey(id) && data.containsValue(pinCode)) {
            return true;
        }
        return false;
    }
}
