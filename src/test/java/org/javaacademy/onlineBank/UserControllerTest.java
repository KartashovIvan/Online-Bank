package org.javaacademy.onlineBank;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.javaacademy.onlineBank.controller.UserController;
import org.javaacademy.onlineBank.dto.UserAuthDtoRq;
import org.javaacademy.onlineBank.dto.UserDtoRq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest extends OnlineBankTests {
    @Autowired
    private UserController userController;

    @Test
    public void createUserSuccess() {
        String pinCode = RestAssured
                .given()
                .body(new UserDtoRq(TELEPHONE_NUMBER, FIO))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/signup")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract()
                .asString();
        Assertions.assertEquals(4, pinCode.length());
    }

    @Test
    public void authSuccess() {
        String pinCode = createAndTakeUserPinCode();
        RestAssured
                .given()
                .body(new UserAuthDtoRq(TELEPHONE_NUMBER, pinCode))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/auth")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    public void createExistUser() {
        createAndTakeUserPinCode();
        RestAssured
                .given()
                .body(new UserDtoRq(TELEPHONE_NUMBER, FIO))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/signup")
                .then()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    @Test
    public void authFail() {
        createAndTakeUserPinCode();
        String incorrectPinCode = "00";
        RestAssured
                .given()
                .body(new UserAuthDtoRq(TELEPHONE_NUMBER, incorrectPinCode))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/auth")
                .then()
                .statusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
