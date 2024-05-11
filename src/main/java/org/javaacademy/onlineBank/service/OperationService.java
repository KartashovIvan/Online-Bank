package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.OperationDtoRs;
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
    private UserService userService;

    public void addOperation(String accountNumber, OperationType type, BigDecimal total, String description) {
        String id = UUID.randomUUID().toString();
        Operation operation = new Operation(id, LocalDateTime.now(), accountNumber, type, total, description);
        operationRepository.addOperation(operation);
    }

    public List<OperationDtoRs> getAllAccountOperation(String accountNumber) {
        return operationRepository.getAllAccountOperation(accountNumber).stream()
                .map(this::convertToOperationDto)
                .toList();
    }

    public List<OperationDtoRs> getAllUserOperation(User user) {
        List<Account> userAccounts = accountRepository.takeAllAccountsUser(user);
        return userAccounts.stream()
                .map(account -> operationRepository.getAllAccountOperation(account.getAccountNumber()))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Operation::getDateTime).reversed())
                .map(this::convertToOperationDto)
                .toList();
    }

    public List<OperationDtoRs> getAllUserOperationByToken(String token) {
        User user = userService.findUserByToken(token);
        return getAllUserOperation(user);
    }

    private OperationDtoRs convertToOperationDto(Operation operation) {
        return new OperationDtoRs(operation.getDateTime(),
                operation.getAccountNumber(),
                operation.getOperationType(),
                operation.getTotal(),
                operation.getDescription()
        );
    }
}
