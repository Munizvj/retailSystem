package com.security_service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDTO {
    private String name;
    private String login;
    private String password;
    private PositionDTO position;
    Set<PermissionDTO> extraPermission;
}
