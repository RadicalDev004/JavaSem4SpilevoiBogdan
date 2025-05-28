package org.example.lab11.controllers;

import org.example.lab11.jwt.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        System.out.println(username + " " + password + " " + "user".equals(username) + " " + "password".equals(password));
        if ("user".equals(username) && "password".equals(password)) {
            String token = JwtService.generateToken(username);

            response.put("token", token);
        } else {
            response.put("error", "Invalid credentials");
        }
        return response;
    }

}
