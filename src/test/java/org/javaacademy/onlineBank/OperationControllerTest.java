package org.javaacademy.onlineBank;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlineBank.controller.OperationController;
import org.javaacademy.onlineBank.dto.DepositDtoRq;
import org.javaacademy.onlineBank.dto.OperationDtoRs;
import org.javaacademy.onlineBank.dto.PaymentDtoRq;
import org.javaacademy.onlineBank.entity.type.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OperationControllerTest extends OnlineBankTests {
    private static final String BASE_OPERATION_CONTROLLER_URL = "/operations";

    @Autowired
    private OperationController operationController;

    @Test
    public void receiveSuccess() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        String accountNumber = createFirstAccount(token);
        doReceive(accountNumber, HttpStatus.ACCEPTED);
    }

    @Test
    public void receiveFail() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        createFirstAccount(token);
        String incorrectAccountNumber = "000000";
        doReceive(incorrectAccountNumber, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void paySuccess() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        String accountNumber = createFirstAccount(token);
        doReceive(accountNumber, HttpStatus.ACCEPTED);
        doPay(token, accountNumber, new BigDecimal("1000"), HttpStatus.ACCEPTED);
    }

    @Test
    public void payFail() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        String accountNumber = createFirstAccount(token);
        doReceive(accountNumber, HttpStatus.ACCEPTED);
        doPay(token, accountNumber, new BigDecimal("3000"), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void checkHistoryOperations() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        String accountNumber = createFirstAccount(token);
        doReceive(accountNumber,HttpStatus.ACCEPTED);
        doPay(token, accountNumber, new BigDecimal("1000"), HttpStatus.ACCEPTED);

        TypeRef<List<OperationDtoRs>> listTypeRef = new TypeRef<>(){};
        List<OperationDtoRs> operations = RestAssured
                .given()
                .get(BASE_OPERATION_CONTROLLER_URL + "?token=" + token)
                .then()
                .extract()
                .body()
                .as(listTypeRef);
        Assertions.assertEquals(2,operations.size());
        Assertions.assertEquals(OperationType.WITHDRAWAL,operations.get(0).getOperationType());
        Assertions.assertEquals(OperationType.CREDIT,operations.get(1).getOperationType());
    }

    private void doReceive(String accountNumber, HttpStatus httpStatus) {
        RestAssured
                .given()
                .body(new DepositDtoRq(
                        accountNumber,
                        new BigDecimal("2000"),
                        "Авсанс"))
                .contentType(ContentType.JSON)
                .post(BASE_OPERATION_CONTROLLER_URL + "/receive")
                .then()
                .statusCode(httpStatus.value());
    }

    private void doPay(String token, String accountNumber, BigDecimal total, HttpStatus httpStatus) {
        RestAssured
                .given()
                .body(new PaymentDtoRq(token,
                        accountNumber,
                        total,
                        "Покупка"))
                .contentType(ContentType.JSON)
                .post(BASE_OPERATION_CONTROLLER_URL + "/pay")
                .then()
                .statusCode(httpStatus.value());
    }
}
