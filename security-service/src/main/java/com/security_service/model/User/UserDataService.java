package com.security_service.model.User;

import com.security_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    }

    @Transactional
    public void deleteById(Long id) {
        var user = this.findById(id);
        userRepository.deleteById(user.getId());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User doLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Failed login attempt for username: {}", login);
                    return new BadCredentialsException("Login or password invalid");
                });
    }
}
