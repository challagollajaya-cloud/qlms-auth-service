package com.microsoft.quantum.qlmsauthservice.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private String role;
    private String email;
    private String lab;
}