package com.rosivaldolucas.ead.authuser_api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rosivaldolucas.ead.authuser_api.dtos.UserDTO;
import com.rosivaldolucas.ead.authuser_api.enums.UserStatus;
import com.rosivaldolucas.ead.authuser_api.enums.UserType;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO
    ) {
        if (this.userService.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }

        if (this.userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }

        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setUserType(UserType.STUDENT);
        newUser.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        newUser.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        this.userService.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
