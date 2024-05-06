package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.entity.Account;
import org.javaacademy.onlineBank.entity.Operation;
import org.javaacademy.onlineBank.entity.User;
import org.javaacademy.onlineBank.entity.type.OperationType;
import org.javaacademy.onlineBank.repository.AccountRepository;
import org.javaacademy.onlineBank.repository.OperationRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OperationService {
    private OperationRepository operationRepository;
    private AccountRepository accountRepository;

    public void addOperation(String accountNumber, OperationType type, BigDecimal total, String description) {
        String id = UUID.randomUUID().toString();
        Operation operation = new Operation(id, LocalDateTime.now(), accountNumber, type, total, description);
        operationRepository.addOperation(operation);
    }

    public List<Operation> getAllAccountOperation(String accountNumber) {
        return operationRepository.getAllAccountOperation(accountNumber);
    }

    public List<Operation> getAllUserOperation(User user) {
        List<Account> userAccounts = accountRepository.takeAllAccountsUser(user);
        //TODO проверить логигу!!!
        return userAccounts.stream()
                .map(account -> operationRepository.getAllAccountOperation(account.getAccountNumber()))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Operation::getDateTime).reversed())
                .toList();
    }
}
