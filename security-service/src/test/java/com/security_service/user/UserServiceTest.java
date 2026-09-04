package com.security_service.user;

import com.security_service.core.UserDataService;
import com.security_service.dto.UserRequestDTO;
import com.security_service.dto.UserResponseDTO;
import com.security_service.mapper.UserMapper;
import com.security_service.domain.User;
import com.security_service.repository.UserRepository;
import com.security_service.security.SecurityService;
import com.security_service.dto.UserLoginDTO;
import com.security_service.security.UserDetailsImpl;
import com.security_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private SecurityService securityService;
    @Mock
    private UserDataService userDataService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    //Test most legible i could do

    @Test
    void shouldLoginSuccessfully() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setLogin("core");
        loginDTO.setPassword("123456");

        User user = new User();
        user.setLogin("core");
        user.setPassword("encrypted-password");


        when(userDataService.findByLogin("core")).thenReturn(user);

        when(passwordEncoder.matches("123456", "encrypted-password"))
                .thenReturn(true);

        when(securityService.generateToken(eq(user), any(UserDetailsImpl.class))).thenReturn("tokenJWT");

        String result = userService.userLogin(loginDTO);

        assertEquals("tokenJWT", result);

        verify(userDataService).findByLogin("core");
        verify(passwordEncoder)
                .matches("123456", "encrypted-password");
        verify(securityService)
                .generateToken(eq(user), any(UserDetailsImpl.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setLogin("core");
        loginDTO.setPassword("123456");

        when(userDataService.findByLogin("core"))
                .thenThrow(new BadCredentialsException("Login or Password invalid"));

        assertThrows(BadCredentialsException.class,
                () -> userService.userLogin(loginDTO));

        verify(userDataService).findByLogin("core");
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(securityService);

    }

    @Test
    void shouldThrowExceptionWhenPasswordWrong() {
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setLogin("core");
        loginDTO.setPassword("123456");

        User user = new User();
        user.setLogin("core");
        user.setPassword("encrypted-password");

        when(userDataService.findByLogin("core")).thenReturn(user);

        when(passwordEncoder.matches("123456", "encrypted-password"))
                .thenReturn(false);

        assertThrows(BadCredentialsException.class
                , () -> userService.userLogin(loginDTO));

        verify(  userDataService).findByLogin("core");
        verify(passwordEncoder).matches("123456", "encrypted-password");
    }

    @Test
    void shouldRegisterSuccessfully() {
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("core");
        request.setPassword("123456");

        User user = new User();
        UserResponseDTO expectedResponse = new UserResponseDTO();

        when(mapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("encrypted-password");
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDTO(user)).thenReturn(expectedResponse);

        UserResponseDTO response = userService.registerUser(request);

        assertEquals(expectedResponse, response);
    }

    @Test
    void shouldUpdateSuccessfully() {
        Long userId = 1L;
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("core");
        request.setPassword("123456");

        User user = new User();
        UserResponseDTO expectedResponse = new UserResponseDTO();
        expectedResponse.setLogin("romeu");
        expectedResponse.setId(userId);

        when(userDataService.findUserById(userId)).thenReturn(user);
        when(passwordEncoder.encode("123456")).thenReturn("encrypted-password");
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.toDTO(user)).thenReturn(expectedResponse);

        UserResponseDTO result = userService.updateUser(userId, request);

        assertEquals(expectedResponse, result);
    }

    @Test
    void shouldDeleteSuccessfully() {
        Long id = 1L;
        User user = new User();

        when(userDataService.findUserById(id)).thenReturn(user);
        userService.deleteUser(id);

        verify(repository).delete(user);
    }


}