package com.user.view;

import java.time.LocalDateTime;

public class AddUserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role; // e.g., "STUDENT", "TEACHER", "ADMIN"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
