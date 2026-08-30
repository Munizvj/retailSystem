package com.security_service.dto;

import com.security_service.model.permission.PermissionName;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PermissionRequest {

    private Long userId;
    private Set<PermissionName> permissionNames;
}
