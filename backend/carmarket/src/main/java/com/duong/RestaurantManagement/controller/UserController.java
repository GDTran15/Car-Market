package com.duong.RestaurantManagement.controller;

import com.duong.RestaurantManagement.dto.user.request.UserRegisterRequestDTO;
import com.duong.RestaurantManagement.model.User;
import com.duong.RestaurantManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        userService.registerANewUser(userRegisterRequestDTO);
        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        return "Success";
//    }

}
