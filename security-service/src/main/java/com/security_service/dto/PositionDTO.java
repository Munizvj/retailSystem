package com.security_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDTO {

    private Long id;
    private String role;
    private Set<PermissionDTO> permissions;

}
