package com.kodilla.airporthater.service;

import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.exception.exceptions.UserNotFoundException;
import com.kodilla.airporthater.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final SecurityService securityService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void blockUser(Long id) throws UserNotFoundException {
        User user = getUserById(id);
        user.setBlocked(true);
        userRepository.save(user);
    }

    public void unblockUser(Long id) throws UserNotFoundException {
        User user = getUserById(id);
        user.setBlocked(false);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    // Metoda wywołująca metodę z SecurityService w celu uzyskania identyfikatora użytkownika
//    public Long getCurrentUserId() {
//        return securityService.getCurrentUserId();
//    }
}
