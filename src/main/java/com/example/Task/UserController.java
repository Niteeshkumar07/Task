package com.example.Task;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @PostMapping("/add")
  public User addUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  @GetMapping
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }

  @Transactional
  @PutMapping("/update/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    return userRepository.findById(id)
            .map(user -> {
              user.setName(userDetails.getName());
              user.setEmail(userDetails.getEmail());
              user.setCity(userDetails.getCity());
              user.setPincode(userDetails.getPincode());

              return userRepository.save(user);
            })
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
  }
  @DeleteMapping("/delete/{id}")
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
