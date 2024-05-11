package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.AccountDtoRs;
import org.javaacademy.onlineBank.entity.Account;
import org.javaacademy.onlineBank.entity.User;
import org.javaacademy.onlineBank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private static int ACCOUNT_NUMBER = 0;
    private AccountRepository accountRepository;

    public Account createAccount(User user) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, user, new BigDecimal("0.00"));
        accountRepository.addAccount(account);
        return account;
    }

    public void putMoney(String accountNumber, BigDecimal total) {
        Account account = findAccount(accountNumber);
        BigDecimal newBalance = account.getBalance().add(total).setScale(2, RoundingMode.HALF_EVEN);
        account.setBalance(newBalance);
    }

    public void withdrawMoney(String accountNumber, BigDecimal total) {
        Account account = findAccount(accountNumber);
        if (total.compareTo(account.getBalance()) > 0) {
            throw new RuntimeException("Operation denied. Insufficient funds for withdrawal");
        }
        BigDecimal newBalance = account.getBalance().subtract(total).setScale(2, RoundingMode.HALF_EVEN);
        account.setBalance(newBalance);
    }

    public List<AccountDtoRs> takeAllAccountsUser(User user) {
        return accountRepository.takeAllAccountsUser(user).stream()
                .map(this::convertToAccountDtoRs)
                .toList();
    }

    public BigDecimal takeBalance(String accountNumber) {
        Account account = findAccount(accountNumber);
        return account.getBalance().setScale(2, RoundingMode.HALF_EVEN);
    }

    public boolean checkOwner(User user, String accountNumber) {
        Account account = findAccount(accountNumber);
        return account.getOwner().equals(user);
    }

    private String generateAccountNumber() {
        ACCOUNT_NUMBER++;
        return String.format("%06d", ACCOUNT_NUMBER);
    }

    private Account findAccount(String accountNumber) {
        return accountRepository.findAccountByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account %s is not exist".formatted(accountNumber)));
    }

    private AccountDtoRs convertToAccountDtoRs(Account account) {
        return new AccountDtoRs(account.getAccountNumber(),
                account.getOwner());
    }
}
