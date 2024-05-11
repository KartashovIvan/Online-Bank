package org.javaacademy.onlineBank.controller;

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
public class OperationController {
    private OperationService operationService;
    private BankService bankService;

    @GetMapping()
    public ResponseEntity<List<OperationDtoRs>> getAllOperations(@RequestParam String token) {
        return ResponseEntity.ok(operationService.getAllUserOperationByToken(token));
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody PaymentDtoRq paymentDtoRq) {
        bankService.makePayment(paymentDtoRq.getAccountNumber(),
                paymentDtoRq.getTotal(),
                paymentDtoRq.getDescription(),
                paymentDtoRq.getToken());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receive(@RequestBody DepositDtoRq depositDtoRq) {
        bankService.makeDeposit(depositDtoRq.getAccountNumber(),
                depositDtoRq.getTotal(),
                depositDtoRq.getDescription());
        return ResponseEntity.accepted().build();
    }
}
