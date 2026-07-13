package com.microsoft.quantum.qlmsauthservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microsoft.quantum.qlmsauthservice.model.User;
import com.microsoft.quantum.qlmsauthservice.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    private Map<String, User> users = new HashMap<>() {{
        put("admin", User.builder()
                .username("admin")
                .password("admin123")
                .role("ADMIN")
                .email("admin@microsoft.com")
                .lab("Redmond")
                .build());
        put("researcher", User.builder()
                .username("researcher")
                .password("research123")
                .role("RESEARCHER")
                .email("researcher@microsoft.com")
                .lab("Sydney")
                .build());
        put("labmanager", User.builder()
                .username("labmanager")
                .password("manager123")
                .role("LAB_MANAGER")
                .email("labmanager@microsoft.com")
                .lab("Redmond")
                .build());
    }};

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        User user = users.get(username);
        if (user == null ||
                !user.getPassword().equals(password)) {
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

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(users.keySet());
    }
}