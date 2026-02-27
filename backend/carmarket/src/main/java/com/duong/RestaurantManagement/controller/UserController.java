package com.duong.RestaurantManagement.controller;

import com.duong.RestaurantManagement.model.User;
import com.duong.RestaurantManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        return service.register(user);
//    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return "Success";
    }

}
