package com.security_service.model.permission;

import com.security_service.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionDataService {

    private final PermissionRepository repository;

    public Permission getPermissionByName(PermissionName name) {
        return repository.findByName(name).orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}
