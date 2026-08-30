package com.security_service.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserRequestDTO {

    private String name;
    private String login;
    private String password;
    private PositionDTO position;
    Set<PermissionDTO> extraPermission;
}
