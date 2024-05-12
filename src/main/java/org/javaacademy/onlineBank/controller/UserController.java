package org.javaacademy.onlineBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.javaacademy.onlineBank.dto.UserAuthDtoRq;
import org.javaacademy.onlineBank.dto.UserDtoRq;
import org.javaacademy.onlineBank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "UserController", description = "Контроллер по регистрации и аутентификации пользователя")
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> signup(@RequestBody UserDtoRq userDtoRq) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.registration(userDtoRq.getFio(),
                userDtoRq.getTelephoneNumber()));
    }

    @PostMapping("/auth")
    @Operation(summary = "Аутентификация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "503",
                    content = @Content(mediaType = "application/text",
                            schema = @Schema(implementation = String.class)))})
    public ResponseEntity<String> authentication(@RequestBody UserAuthDtoRq userAuthDtoRq) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.authentication(userAuthDtoRq.getTelephoneNumber(),
                userAuthDtoRq.getPinCode()));
    }
}
