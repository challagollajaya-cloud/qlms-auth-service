package com.microsoft.quantum.qlmsauthservice.controller;

import com.microsoft.quantum.qlmsauthservice.model.LoginRequest;
import com.microsoft.quantum.qlmsauthservice.model.User;
import com.microsoft.quantum.qlmsauthservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // Hardcoded users for now
    // In production: use database!
    private Map<String, User> users = new HashMap<>() {{
        put("admin", User.builder()
                .username("admin")
                .password("AdminQlms@2024!")
                .role("ADMIN")
                .email("admin@microsoft.com")
                .lab("Redmond")
                .build());
        put("researcher", User.builder()
                .username("researcher")
                .password("ResearchQlms@2024!")
                .role("RESEARCHER")
                .email("researcher@microsoft.com")
                .lab("Sydney")
                .build());
        put("labmanager", User.builder()
                .username("labmanager")
                .password("LabMgrQlms@2024!")
                .role("LAB_MANAGER")
                .email("labmanager@microsoft.com")
                .lab("Redmond")
                .build());
    }};

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        User user = users.get(request.getUsername());

        if (user == null ||
                !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401)
                    .body("Invalid username or password!");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(), user.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("lab", user.getLab());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok("Token is valid!");
        }
        return ResponseEntity.status(401)
                .body("Invalid token!");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(users.keySet());
    }
}