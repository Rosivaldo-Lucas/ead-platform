package com.rosivaldolucas.ead.authuser_api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rosivaldolucas.ead.authuser_api.dtos.UserDTO;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable UUID id) {
        Optional<User> userOptional = this.userService.findById(id);

        return userOptional.<ResponseEntity<Object>>
                map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID id,
            @RequestBody @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO
    ) {
        Optional<User> userOptional = this.userService.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            this.userService.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable UUID id,
            @RequestBody @JsonView(UserDTO.UserView.PasswordChangePut.class) UserDTO userDTO
    ) {
        Optional<User> userOptional = this.userService.findById(id);

        if (userOptional.isPresent()) {
            if (!userDTO.getOldPassword().equals(userOptional.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Mismatched old passwords!");
            }

            User user = userOptional.get();
            user.setPassword(userDTO.getPassword());
            user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            this.userService.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateImage(
            @PathVariable UUID id,
            @RequestBody @JsonView(UserDTO.UserView.ImageChangePut.class) UserDTO userDTO
    ) {
        Optional<User> userOptional = this.userService.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setImageUrl(userDTO.getImageUrl());
            user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

            this.userService.save(user);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        Optional<User> userOptional = this.userService.findById(id);

        if (userOptional.isPresent()) {
            this.userService.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

}
