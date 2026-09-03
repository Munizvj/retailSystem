package com.security_service.service;

import com.security_service.core.UserDataService;
import com.security_service.dto.PermissionDTO;
import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import com.security_service.mapper.UserMapper;
import com.security_service.model.Permission;
import com.security_service.model.Position;
import com.security_service.model.User;
import com.security_service.repository.PermissionRepository;
import com.security_service.repository.PositionRepository;
import com.security_service.repository.UserRepository;
import com.security_service.security.SecurityService;
import com.security_service.dto.UserLoginDTO;
import com.security_service.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper mapper;
    private final SecurityService securityService;
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    public String userLogin(UserLoginDTO loginDTO) {
        User user = userDataService.findByLogin(loginDTO.getLogin());

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            log.warn("Failed login attempt for username: {}", loginDTO.getLogin());
            throw new BadCredentialsException("Invalid login or password");
        }

        log.info("User {} succesfully authenticated!", user.getLogin());

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return securityService.generateToken(user, userDetails);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> userList = userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

        return userList;
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getPosition() != null && dto.getPosition().getId() != null) {
            Position position = userDataService.findPositionById(dto.getPosition().getId());
            user.setPosition(position);
        }

        if (dto.getExtraPermission() != null && !dto.getExtraPermission().isEmpty()) {
            Set<Long> permissionIds = dto.getExtraPermission().stream()
                    .map(PermissionDTO::getId)
                    .collect(Collectors.toSet());

            List<Permission> extraPermissions = permissionRepository.findAllById(permissionIds);
            user.setExtraPermission(new HashSet<>(extraPermissions));
        }

        User savedUser = userRepository.save(user);

        log.info("User registered with success with the login: {}", user.getLogin());
        return mapper.toDTO(savedUser);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existingUser = userDataService.findUserById(id);
        mapper.updateEntity(dto, existingUser);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getPosition() != null && dto.getPosition().getId() != null) {
            Position position = userDataService.findPositionById(dto.getPosition().getId());
            existingUser.setPosition(position);
        }

        if (dto.getExtraPermission() != null) {
            Set<Long> permissionIds = dto.getExtraPermission().stream()
                    .map(PermissionDTO::getId)
                    .collect(Collectors.toSet());

            List<Permission> extraPermissions = permissionRepository.findAllById(permissionIds);
            existingUser.setExtraPermission(new HashSet<>(extraPermissions));
        }

        User updatedUser = userRepository.save(existingUser);
        log.info("User {} successfully updated", existingUser.getLogin());
        return mapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userDataService.findUserById(id);

        userRepository.delete(user);
    }
}
