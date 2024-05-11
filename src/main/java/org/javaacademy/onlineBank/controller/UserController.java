package org.javaacademy.onlineBank.controller;

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
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDtoRq userDtoRq) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.registration(userDtoRq.getFio(),
                userDtoRq.getTelephoneNumber()));
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authentication(@RequestBody UserAuthDtoRq userAuthDtoRq) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.authentication(userAuthDtoRq.getTelephoneNumber(),
                userAuthDtoRq.getPinCode()));
    }
}
