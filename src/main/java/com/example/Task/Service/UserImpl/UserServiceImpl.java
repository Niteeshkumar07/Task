package com.example.Task.Service.UserImpl;

import com.example.Task.Repository.UserRepository;
import com.example.Task.Service.UserService;
import com.example.Task.User;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    @Override
    public User updateUser(Long id, User userDetails) {
//        try {
//            return userRepository.findById(id)
//                    .map(user -> {
//                        user.setName(userDetails.getName());
//                        user.setEmail(userDetails.getEmail());
//                        user.setCity(userDetails.getCity());
//                        user.setPincode(userDetails.getPincode());
//                        return userRepository.save(user);
//                    })
//                    .orElseThrow(() -> new RuntimeException("User not found with id " + id));
//        } catch (OptimisticLockException e) {
//            throw new RuntimeException("Update failed: User was modified by someone else");
//        }
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        // Optional: check version manually (if client sends version)
        if (!existingUser.getVersion().equals(userDetails.getVersion())) {
            throw new RuntimeException("Update failed: User was modified by someone else");
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setCity(userDetails.getCity());
        existingUser.setPincode(userDetails.getPincode());

        return userRepository.save(existingUser);
    }

    @Override
    public User updateUserWithOldEntity(User user) {
        try {
            return userRepository.save(user); // uses version for optimistic locking
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Update failed: User was modified by someone else");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist");
        }
        userRepository.deleteById(id);
    }

}
