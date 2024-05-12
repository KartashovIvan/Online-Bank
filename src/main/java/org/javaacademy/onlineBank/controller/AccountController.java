package org.javaacademy.onlineBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "AccountController", description = "Контроллер по работе с банковскими счетами пользователя")
public class AccountController {
    private UserService userService;
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "Показать все счета пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AccountDtoRs.class)))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<List<AccountDtoRs>> takeAllAccountsUser(@RequestParam @Parameter(name = "Токен пользователя") String token) {
        return ResponseEntity.ok(accountService.takeAllAccountsUser(userService.findUserByToken(token)));
    }

    @GetMapping("{accountNumber}")
    @Operation(summary = "Просмотр баланса пользователя по номеру счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<BigDecimal> takeBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.takeBalance(accountNumber));
    }

    @PostMapping
    @Operation(summary = "Создание банковского счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> createAccount(@RequestBody Map<String, String> data) {
        Account account = accountService.createAccount(userService.findUserByToken(data.get("token")));
        return ResponseEntity.status(HttpStatus.CREATED).body(account.getAccountNumber());
    }
}
