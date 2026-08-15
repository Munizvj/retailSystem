package com.security_service.user;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String login;
    private String password;
    private UserRole userRole;
}
