package com.example.grappler.Controller;

import com.example.grappler.Entity.Users;
import com.example.grappler.Exception.InvalidUrlException;
import com.example.grappler.Exception.UserNotFoundException;
import com.example.grappler.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    Logger logger= LoggerFactory.getLogger(UserController.class);
    /**
     * Get a list of all users.
     *
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            List<Users> users = userRepository.findAll();
            logger.info("Retrieved all users.");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving users.", e);
throw  new RuntimeException("Unable to fetch ",e);        }
    }


    /**
     * Create a new user.
     *
     * @param users The user data to create.
     * @return ResponseEntity
     */
    @PostMapping("/")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        try {
            Users newUser = userRepository.save(users);
            logger.info("Created a new user with ID: {}", newUser.getId());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating a new user.", e);
        throw new RuntimeException("Failed to add user",e);        }
    }

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user to fetch.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        try {
            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent()) {
                logger.info("Retrieved user with ID: {}", id);
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
               throw new UserNotFoundException("User with ID "+id+"not found");
            }
        } catch (UserNotFoundException ex) {
            logger.error("Error while retrieving user with ID: " + id, ex);
throw new RuntimeException("failed to retrievve user");        }
    }

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity .
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            logger.info("Deleted user with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            logger.error("Error while deleting user with ID: " + id, e);
            throw new RuntimeException("Failed to delete ticket",e);
        }
    }


    /**
     * Update a user's information by their ID.
     *
     * @param id          The ID of the user to update.
     * @param updatedUser The updated user data.
     * @return ResponseEntity containing the updated user or a "Not Found" response if the user does not exist, or an error response if an exception occurs.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateStudent(@PathVariable Long id, @RequestBody Users updatedUser) {
        try {
            Optional<Users> updateUser = userRepository.findById(id);
            if (updateUser.isPresent()) {
                Users existingUser = updateUser.get();
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
                userRepository.save(existingUser);
                logger.info("Updated user with ID: {}", id);
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            } else {
                logger.warn("User with ID {} not found for update.", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating user with ID: " + id, e);
throw new RuntimeException("Unable to update",e);
        }
    }
}
