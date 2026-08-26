package com.security_service.service;

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
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserService {

    private final UserRepository userRepository;
    private final PositionRepository  positionRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper mapper;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public String userLogin(UserLoginDTO loginDTO){
        User user = userRepository.findByLogin(loginDTO.getLogin())
                .orElseThrow(() ->{
                    log.warn("Failed login attempt for username: {}", loginDTO.getLogin());
                    return new BadCredentialsException("Login or password invalid");
                });

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            log.warn("Failed login attempt for username: {}", loginDTO.getLogin());
            throw new BadCredentialsException("Invalid login or password");
        }

        log.info("User {} succesfully authenticated!", user.getLogin());
        return securityService.generateToken(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        List<UserResponseDTO> userList = userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

        return userList;
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto){
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getPosition() != null && dto.getPosition().getId() != null){
            Position position = positionRepository.findById(dto.getPosition().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado com o ID: " + dto.getPosition().getId()));;
            user.setPosition(position);
        }

        if (dto.getExtraPermission() != null && !dto.getExtraPermission().isEmpty()){
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
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        mapper.updateEntity(dto, existingUser);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()){
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getPosition() != null && dto.getPosition().getId() != null){
            Position position = positionRepository.findById(dto.getPosition().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado"));
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
    public void deleteUser(Long id){
    }
}
