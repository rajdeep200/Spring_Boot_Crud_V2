package com.jwt.example.jwtExample.services;

import com.jwt.example.jwtExample.Entity.User;
import com.jwt.example.jwtExample.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserByID(Long id) {
        Optional<User> retrievedUser = userRepository.findById(id);
        if(retrievedUser.isPresent()) {
            return retrievedUser.get();
        }else {
            throw new ResolutionException("User not found with ID: " + id);
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> retrievedUser = userRepository.findByEmail(email);
        if(retrievedUser.isPresent()) {
            return retrievedUser.get();
        }else {
            throw new ResolutionException("User not found with Email: " + email);
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserByID(id);
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getPassword());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUserByID(Long id) {
        User user = getUserByID(id);
        userRepository.deleteById(id);
    }
}
