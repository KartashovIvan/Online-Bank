package org.javaacademy.onlineBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.TransferDtoRq;
import org.javaacademy.onlineBank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
@Tag(name = "BankController", description = "Контроллер банка")
public class BankController {
    private BankService bankService;

    @GetMapping("/bank-info")
    @Operation(summary = "Информация о банке")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<String> bankInfo() {
        return ResponseEntity.ok(bankService.bankInfo());
    }

    @PostMapping("/transfer")
    @Operation(summary = "Перевод на счет в другой банк")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202"),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<?> transferTo(@RequestBody TransferDtoRq transferDtoRq) {
        bankService.transfersTo(transferDtoRq.getToken(),
                transferDtoRq.getTotal(),
                transferDtoRq.getDescription(),
                transferDtoRq.getFromAccountNumber(),
                transferDtoRq.getToAccountNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
