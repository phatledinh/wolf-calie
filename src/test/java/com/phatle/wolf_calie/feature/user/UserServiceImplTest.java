package com.phatle.wolf_calie.feature.user;

import com.phatle.wolf_calie.exception.DuplicateResourceException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.user.dto.CreateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UpdateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found")
    void getUserById_notFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    @DisplayName("Should return user when found")
    void getUserById_found_returnsUserResponse() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("Test User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserById(1L);

        assertEquals("test@test.com", result.email());
        assertEquals("Test User", result.name());
    }

    @Test
    @DisplayName("Should create user successfully")
    void createUser_success_returnsUserResponse() {
        CreateUserRequest request = new CreateUserRequest("Test Name", "test@test.com", "password123", "1234567890");

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByPhone(request.phone())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(request.name());
        savedUser.setEmail(request.email());
        savedUser.setPhone(request.phone());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse result = userService.createUser(request);

        assertEquals(1L, result.id());
        assertEquals("test@test.com", result.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when email exists during creation")
    void createUser_duplicateEmail_throwsException() {
        CreateUserRequest request = new CreateUserRequest("Test Name", "test@test.com", "password123", "1234567890");

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(request));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when phone exists during creation")
    void createUser_duplicatePhone_throwsException() {
        CreateUserRequest request = new CreateUserRequest("Test Name", "test@test.com", "password123", "1234567890");

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByPhone(request.phone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(request));
    }

    @Test
    @DisplayName("Should update user successfully")
    void updateUser_success_returnsUserResponse() {
        UpdateUserRequest request = new UpdateUserRequest("Updated Name", "0987654321", Gender.MALE, null, null);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");
        existingUser.setPhone("1234567890");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByPhone(request.phone())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse result = userService.updateUser(1L, request);

        assertEquals("Updated Name", result.name());
        assertEquals("0987654321", result.phone());
        assertEquals(Gender.MALE, result.gender());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when updating with existing phone")
    void updateUser_duplicatePhone_throwsException() {
        UpdateUserRequest request = new UpdateUserRequest("Updated Name", "0987654321", Gender.MALE, null, null);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setPhone("1234567890");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByPhone(request.phone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.updateUser(1L, request));
    }

    @Test
    @DisplayName("Should soft delete user successfully")
    void deleteUser_success_softDeletesUser() {
        User existingUser = new User();
        existingUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        
        userService.deleteUser(1L);

        assertNotNull(existingUser.getDeletedAt());
        verify(userRepository, times(1)).save(existingUser);
    }
}
