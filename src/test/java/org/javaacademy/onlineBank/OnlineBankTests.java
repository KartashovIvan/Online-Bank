package org.javaacademy.onlineBank;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.onlineBank.dto.UserAuthDtoRq;
import org.javaacademy.onlineBank.dto.UserDtoRq;

import java.util.HashMap;
import java.util.Map;

class OnlineBankTests {
    protected static final String BASE_USER_CONTROLLER_URL = "/user";
    protected static final String BASE_ACCOUNT_CONTROLLER_URL = "/account";
    protected static final String FIO = "TEST_NAME";
    protected static final String TELEPHONE_NUMBER = "09998887766";

    public String createAndTakeUserPinCode() {
        return RestAssured
                .given()
                .body(new UserDtoRq(TELEPHONE_NUMBER, FIO))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/signup")
                .then()
                .extract()
                .body()
                .asString();
    }

    public String authAndTakeUserToken(String pinCode) {
        return RestAssured
                .given()
                .body(new UserAuthDtoRq(TELEPHONE_NUMBER, pinCode))
                .contentType(ContentType.JSON)
                .post(BASE_USER_CONTROLLER_URL + "/auth")
                .then()
                .extract()
                .body()
                .asString();
    }

    public String createFirstAccount(String token) {
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return RestAssured
                .given()
                .body(data)
                .contentType(ContentType.JSON)
                .post(BASE_ACCOUNT_CONTROLLER_URL)
                .then()
                .extract()
                .body()
                .asString();
    }

}
