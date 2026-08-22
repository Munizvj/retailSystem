package com.security_service.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String login;
    private String password;
}
