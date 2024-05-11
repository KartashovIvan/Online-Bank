package org.javaacademy.onlineBank.controller;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.TransferDtoRq;
import org.javaacademy.onlineBank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class BankController {
    private BankService bankService;

    @GetMapping("/bank-info")
    public ResponseEntity<String> bankInfo() {
        return ResponseEntity.ok(bankService.bankInfo());
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferTo(@RequestBody TransferDtoRq transferDtoRq) {
        bankService.transfersTo(transferDtoRq.getToken(),
                transferDtoRq.getTotal(),
                transferDtoRq.getDescription(),
                transferDtoRq.getFromAccountNumber(),
                transferDtoRq.getToAccountNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
