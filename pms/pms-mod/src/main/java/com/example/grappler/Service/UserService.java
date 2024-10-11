package com.example.grappler.Service;

import com.example.grappler.Entity.Users;
import com.example.grappler.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it) and rethrow or return a custom response
            throw new RuntimeException("Failed to fetch all users", e);
        }
    }

    public Users createUser(Users users) {
        try {
            return userRepository.save(users);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it) and rethrow or return a custom response
            throw new RuntimeException("Failed to create a new user", e);
        }
    }

    public Optional<Users> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it) and rethrow or return a custom response
            throw new RuntimeException("Failed to fetch user by ID", e);
        }
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it) and rethrow or return a custom response
            throw new RuntimeException("Failed to delete user by ID", e);
        }
    }

    public Users updateStudent(Long id, Users updatedUser) {
        Optional<Users> updateUser = getUserById(id);
        if (updateUser.isPresent()) {
            Users existingUser = updateUser.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            try {
                return userRepository.save(existingUser);
            } catch (Exception e) {
                // Handle the exception appropriately (e.g., log it) and rethrow or return a custom response
                throw new RuntimeException("Failed to update user", e);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}
