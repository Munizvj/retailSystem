package com.security_service.core;

import com.security_service.model.Position;
import com.security_service.model.User;
import com.security_service.repository.PositionRepository;
import com.security_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Failed login attempt for username: {}", login);
                    return new BadCredentialsException("Login or password invalid");
                });
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found for Id: " + id));

    }

    public Position findPositionById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found for ID: : " + id));
    }


}
