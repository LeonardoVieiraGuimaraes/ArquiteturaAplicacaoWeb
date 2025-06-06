package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setEnabled(user.isEnabled());
            existingUser.setRoles(user.getRoles());
            existingUser.setNomeCompleto(user.getNomeCompleto());
            existingUser.setCpf(user.getCpf());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        });
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
