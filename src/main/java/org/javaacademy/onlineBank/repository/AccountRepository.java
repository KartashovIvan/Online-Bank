package org.javaacademy.onlineBank.repository;

import org.javaacademy.onlineBank.entity.Account;
import org.javaacademy.onlineBank.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Optional<Account> findAccountByNumber(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public List<Account> takeAllAccountsUser(User user) {
        return accounts.values().stream()
                .filter(account -> account.getOwner().getId().equals(user.getId()))
                .toList();
    }
}
