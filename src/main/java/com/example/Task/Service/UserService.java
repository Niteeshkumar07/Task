package com.example.Task.Service;

import com.example.Task.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
    User getUserById(Long id);
    User updateUser(Long id, User userDetails);



    void deleteUser(Long id);

    // For only Optimistic locking Checking
    User updateUserWithOldEntity(User user);

    // This is for pessimistic locking
    User updateUserPessimistic(Long id, User userDetails);
}
