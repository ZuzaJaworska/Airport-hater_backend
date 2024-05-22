package com.kodilla.airporthater.controller;

import com.kodilla.airporthater.domain.dto.UserDto;
import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.exception.exceptions.FailedToBlockUserException;
import com.kodilla.airporthater.exception.exceptions.FailedToCreateUserException;
import com.kodilla.airporthater.exception.exceptions.UserNotFoundException;
import com.kodilla.airporthater.mapper.UserMapper;
import com.kodilla.airporthater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    // GET
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUserDtoList(users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.getUserById(userId)));
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) throws FailedToCreateUserException {
        User user = userMapper.mapToUser(userDto);
        try {
            userService.createUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new FailedToCreateUserException();
        }
    }

    // PUT
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        User updatedUser = userService.createUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(updatedUser));
    }

    @PutMapping("/block/{userId}")
    public ResponseEntity<Void> blockUser(@PathVariable("userId") Long userId) throws FailedToBlockUserException {
        try {
            userService.blockUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new FailedToBlockUserException();
        }
    }

    @PutMapping("/unblock/{userId}")
    public ResponseEntity<Void> unblockUser(@PathVariable("userId") Long userId) {
        try {
            userService.unblockUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
