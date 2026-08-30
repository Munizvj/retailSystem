package com.security_service.controller;

import com.security_service.dto.PermissionRequest;
import com.security_service.dto.UserLoginDTO;
import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import com.security_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto) {
        String token = service.userLogin(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_USER')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        UserResponseDTO response = service.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = service.updateUser(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/permission")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER')")
    public ResponseEntity<Void> addPermission(@RequestBody PermissionRequest permissionRequest) {
        service.addPermission(permissionRequest);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/verify-can-pay")
    @PreAuthorize("hasAnyAuthority('PAY_NEW_ORDER')")
    public ResponseEntity<Boolean> canPay(){
        return ResponseEntity.ok(Boolean.TRUE);
    }


}
