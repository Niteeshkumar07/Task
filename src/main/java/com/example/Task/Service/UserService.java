package com.example.Task.Service;

import com.example.Task.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
    User getUserById(Long id);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);

    // For only Optimistic locking Checking
    User updateUserWithOldEntity(User user);
}
