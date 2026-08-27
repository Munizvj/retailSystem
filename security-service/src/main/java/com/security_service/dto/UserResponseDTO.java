package com.security_service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String login;
    private PositionDTO position;
    Set<PermissionDTO> extraPermission;
}
