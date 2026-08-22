package com.security_service.service;

import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import com.security_service.mapper.UserMapper;
import com.security_service.model.User;
import com.security_service.repository.UserRepository;
import com.security_service.security.SecurityService;
import com.security_service.dto.UserLoginDTO;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public String userLogin(UserLoginDTO loginDTO){
        User user = repository.findByLogin(loginDTO.getLogin())
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

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto){
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        log.info("User registered with success with the login: {}", user.getLogin());
        return mapper.toDTO(repository.save(user));
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto){
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()){
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        mapper.updateEntity(dto, existingUser);
        log.info("User {} successfully updated", existingUser.getLogin());

        User updatedUser = repository.save(existingUser);

        return mapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not Found"));

        repository.delete(user);
        log.info("User {} successfully deleted", user.getLogin());

    }

}
