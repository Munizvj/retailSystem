package com.security_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String name;
    private String login;
    @NonNull
    private String password;
    private PositionDTO position;
    Set<PermissionDTO> extraPermission;
}
