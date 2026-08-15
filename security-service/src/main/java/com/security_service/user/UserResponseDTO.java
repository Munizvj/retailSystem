package com.security_service.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String login;
    private UserRole userRole;
}
