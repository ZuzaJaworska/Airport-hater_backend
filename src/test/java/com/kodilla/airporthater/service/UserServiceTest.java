package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.exception.exceptions.UserNotFoundException;
import com.kodilla.airporthater.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllUsers() {
        // Given
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(users, result);
    }

    @Test
    public void shouldGetUserById() throws UserNotFoundException {
        // Given
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.getUserById(userId);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenGetUserByIdWithNonExistingId() {
        // Given
        long nonExistingUserId = 999L;
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(nonExistingUserId));
    }

    @Test
    public void shouldCreateUser() {
        // Given
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // When
        User result = userService.createUser(user);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void shouldBlockUser() throws UserNotFoundException {
        // Given
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.blockUser(userId);

        // Then
        assertTrue(user.isBlocked());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldUnblockUser() throws UserNotFoundException {
        // Given
        long userId = 1L;
        User user = new User();
        user.setBlocked(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.unblockUser(userId);

        // Then
        assertFalse(user.isBlocked());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldDeleteUser() throws UserNotFoundException {
        // Given
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).delete(user);
    }
}
