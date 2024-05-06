package org.javaacademy.onlineBank.repository;

import org.javaacademy.onlineBank.entity.Operation;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class OperationRepository {
    private final SortedSet<Operation> history = new TreeSet<>(Comparator.comparing(Operation::getDateTime).reversed());

    public void addOperation(Operation operation) {
        history.add(operation);
    }

    public List<Operation> getAllAccountOperation(String accountNumber) {
        return history.stream()
                .filter(operation -> operation.getAccountNumber().equals(accountNumber))
                .toList();
    }
}
