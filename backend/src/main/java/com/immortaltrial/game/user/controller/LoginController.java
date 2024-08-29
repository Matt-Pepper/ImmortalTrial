package com.immortaltrial.game.user.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @GetMapping
    public Map<String, String> login() {
        Map<String, String> response = new HashMap<>();

        response.put("message", "Login endoint is working!");
        return response;
    }
}
