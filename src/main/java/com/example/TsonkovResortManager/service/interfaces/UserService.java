package com.example.TsonkovResortManager.service.interfaces;

import com.example.TsonkovResortManager.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserById(Long id);
    List<User> findAllUsers();
    void deleteUser(User user);
    void deleteUserById(Long id);
    void updateUser(User user);
}
