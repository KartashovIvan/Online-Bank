package org.javaacademy.onlineBank.controller;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.AccountDtoRs;
import org.javaacademy.onlineBank.entity.Account;
import org.javaacademy.onlineBank.service.AccountService;
import org.javaacademy.onlineBank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {
    private UserService userService;
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDtoRs>> takeAllAccountsUser(@RequestParam String token) {
        return ResponseEntity.ok(accountService.takeAllAccountsUser(userService.findUserByToken(token)));
    }

    @GetMapping("{accountNumber}")
    public ResponseEntity<BigDecimal> takeBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.takeBalance(accountNumber));
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Map<String, String> data) {
        Account account = accountService.createAccount(userService.findUserByToken(data.get("token")));
        return ResponseEntity.status(HttpStatus.CREATED).body(account.getAccountNumber());
    }
}
