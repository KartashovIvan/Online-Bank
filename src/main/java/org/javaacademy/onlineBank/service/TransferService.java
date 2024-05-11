package org.javaacademy.onlineBank.service;

import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.config.BankConfig;
import org.javaacademy.onlineBank.dto.DepositDtoRq;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransferService {
    private RestTemplate restTemplate;
    private BankConfig bankConfig;

    public void transfersTo(String bankName,
                            BigDecimal total,
                            String description,
                            String fio,
                            String accountNumber) {
        String resourceUrl = bankConfig.getPartnerUrl() + "/operations/receive";
        HttpEntity<DepositDtoRq> request = new HttpEntity<>(new DepositDtoRq(accountNumber,
                total,
                "From bank: %s. From person: %s. Description: %s".formatted(bankName, fio, description)));
        ResponseEntity<DepositDtoRq> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, DepositDtoRq.class);

        if (response.getStatusCode().value() != 202) {
            throw new RuntimeException("Transfer to account %s is not possible!".formatted(accountNumber));
        }
    }
}
