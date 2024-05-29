package com.kodilla.airporthater.domain.entity;

import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.AirportScoreAvgRepository;
import com.kodilla.airporthater.repository.CommentRepository;
import com.kodilla.airporthater.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportScoreAvgRepository scoreAvgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User userOne;
    private User userTwo;

    @BeforeEach
    public void setUp() {
        scoreAvgRepository.deleteAll();
        commentRepository.deleteAll();
        airportRepository.deleteAll();
        userRepository.deleteAll();

        userOne = User.builder()
                .username("usernameTest")
                .password("passwordTest")
                .build();
        userTwo = User.builder()
                .username("usernameTwo")
                .password("passwordTwo")
                .build();
    }

    @AfterEach
    public void tearDown() {
        scoreAvgRepository.deleteAll();
        commentRepository.deleteAll();
        airportRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        // Given

        // When
        userRepository.save(userOne);
        Optional<User> userResult = userRepository.findById(userOne.getId());

        // Then
        assertTrue(userResult.isPresent());
        assertEquals(userOne.getId(), userResult.get().getId());
        assertEquals(userOne.getUsername(), userResult.get().getUsername());
        assertEquals(userOne.getPassword(), userResult.get().getPassword());
    }

    @Test
    public void testGetAllUsers() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertEquals(2, users.size());
        assertTrue(users.contains(userOne));
        assertTrue(users.contains(userTwo));
    }

    @Test
    public void testGetUserById() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);

        // When
        Optional<User> userResultOne = userRepository.findById(userOne.getId());
        Optional<User> userResultTwo = userRepository.findById(userTwo.getId());

        // Then
        assertTrue(userResultOne.isPresent());
        assertTrue(userResultTwo.isPresent());
        assertEquals(userOne.getId(), userResultOne.get().getId());
        assertEquals(userTwo.getId(), userResultTwo.get().getId());
    }

    @Test
    public void testUpdateUser() {
        // Given
        userRepository.save(userOne);

        // When
        User updatedUser = userRepository.findById(userOne.getId()).get();
        updatedUser.setUsername("updatedUsername");
        updatedUser.setPassword("updatedPassword");
        userRepository.save(updatedUser);
        Optional<User> userResult = userRepository.findById(userOne.getId());

        // Then
        assertTrue(userResult.isPresent());
        assertEquals(updatedUser.getId(), userResult.get().getId());
        assertEquals(updatedUser.getUsername(), userResult.get().getUsername());
        assertEquals(updatedUser.getPassword(), userResult.get().getPassword());
    }

    @Test
    public void testDeleteUserById() {
        // Given
        userRepository.save(userOne);
        userRepository.save(userTwo);

        // When
        userRepository.deleteById(userTwo.getId());

        // Then
        assertEquals(1, userRepository.findAll().size());
        assertTrue(userRepository.findAll().contains(userOne));
        assertFalse(userRepository.findAll().contains(userTwo));
    }
}