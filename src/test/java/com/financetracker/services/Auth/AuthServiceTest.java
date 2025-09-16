package com.financetracker.services.Auth;

import com.financetracker.dto.login.LoginRequest;
import com.financetracker.entity.User;
import com.financetracker.repository.UserRepository;
import com.financetracker.services.JWT.JwtService;
import com.financetracker.services.UserServices.CustomUserDetailsService;
import com.financetracker.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CustomUserDetailsService userDetailsService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_ShouldCreateNewUser_WhenUserDoesNotExist() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User createdUser = authService.registerUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("test@test.com", createdUser.getEmail());
        assertEquals("hashedPassword", createdUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserExists() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(user));
        verify(userRepository, never()).save(any(User.class));
    }
}