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
import org.javaacademy.onlineBank.dto.DepositDtoRq;
import org.javaacademy.onlineBank.dto.OperationDtoRs;
import org.javaacademy.onlineBank.dto.PaymentDtoRq;
import org.javaacademy.onlineBank.service.BankService;
import org.javaacademy.onlineBank.service.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
@Tag(name = "OperationController", description = "Контроллер по банковским операциям")
public class OperationController {
    private OperationService operationService;
    private BankService bankService;

    @GetMapping()
    @Operation(summary = "Просмотр всех операция пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OperationDtoRs.class)))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<List<OperationDtoRs>> getAllOperations(@RequestParam @Parameter(name = "Токен пользователя") String token) {
        return ResponseEntity.ok(operationService.getAllUserOperationByToken(token));
    }

    @PostMapping("/pay")
    @Operation(summary = "Совершить платеж")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202"),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<?> pay(@RequestBody PaymentDtoRq paymentDtoRq) {
        bankService.makePayment(paymentDtoRq.getAccountNumber(),
                paymentDtoRq.getTotal(),
                paymentDtoRq.getDescription(),
                paymentDtoRq.getToken());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/receive")
    @Operation(summary = "Пополнение счета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202"),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<?> receive(@RequestBody DepositDtoRq depositDtoRq) {
        bankService.makeDeposit(depositDtoRq.getAccountNumber(),
                depositDtoRq.getTotal(),
                depositDtoRq.getDescription());
        return ResponseEntity.accepted().build();
    }
}
