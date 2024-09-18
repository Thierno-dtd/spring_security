package com.springsecurity.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/user")
    public String getUser() {
         return "user";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "user";
    }
}
