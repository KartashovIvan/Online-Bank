package org.javaacademy.onlineBank;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlineBank.controller.AccountController;
import org.javaacademy.onlineBank.dto.AccountDtoRs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountControllerTest extends OnlineBankTests {
    @Autowired
    private AccountController accountController;

    @Test
    public void takeAllAccountSuccess() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        createFirstAccount(token);
        TypeRef<List<AccountDtoRs>> listAccounts = new TypeRef<>() {
        };
        List<AccountDtoRs> accounts = RestAssured
                .given()
                .get(BASE_ACCOUNT_CONTROLLER_URL + "?token=" + token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(listAccounts);
        Assertions.assertEquals(1, accounts.size());
    }

    @Test
    public void checkBalanceSuccess() {
        String pinCode = createAndTakeUserPinCode();
        String token = authAndTakeUserToken(pinCode);
        createFirstAccount(token);
        String balance = RestAssured
                .given()
                .get(BASE_ACCOUNT_CONTROLLER_URL + "/000001")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .asString();
        Assertions.assertEquals("0.00", balance);
    }
}
