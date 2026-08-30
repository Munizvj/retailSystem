package com.security_service.repository;

import com.security_service.model.permission.Permission;
import com.security_service.model.permission.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(PermissionName name);

}
