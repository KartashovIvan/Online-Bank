package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.config.BankConfig;
import org.javaacademy.onlineBank.dto.OperationDtoRs;
import org.javaacademy.onlineBank.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.javaacademy.onlineBank.entity.type.OperationType.CREDIT;
import static org.javaacademy.onlineBank.entity.type.OperationType.WITHDRAWAL;

@Service
@AllArgsConstructor
public class BankService {
    private UserService userService;
    private AccountService accountService;
    private OperationService operationService;
    private BankConfig bankConfig;
    private TransferService transferService;

    public void makePayment(String accountNumber, BigDecimal total, String description, String token) {
        User user = userService.findUserByToken(token);
        if (!accountService.checkOwner(user, accountNumber)) {
            throw new RuntimeException("User %s does not own the account %s".formatted(user.getFio(), accountNumber));
        }
        accountService.withdrawMoney(accountNumber, total);
        operationService.addOperation(accountNumber, WITHDRAWAL, total, description);
    }

    public List<OperationDtoRs> historyWithdraw(String token) {
        User user = userService.findUserByToken(token);
        return operationService.getAllUserOperation(user).stream()
                .filter(operation -> operation.getOperationType().equals(WITHDRAWAL))
                .toList();
    }

    public void makeDeposit(String accountNumber, BigDecimal total, String description) {
        accountService.putMoney(accountNumber, total);
        operationService.addOperation(accountNumber, CREDIT, total, description);
    }

    public String bankInfo() {
        return bankConfig.getName();
    }

    public void transfersTo(String token,
                            BigDecimal total,
                            String description,
                            String fromAccountNumber,
                            String toAccountNumber) {
        String bankName = bankConfig.getName();
        User user = userService.findUserByToken(token);
        makePayment(fromAccountNumber, total, description, token);
        transferService.transfersTo(bankName, total, description, user.getFio(), toAccountNumber);
    }
}
