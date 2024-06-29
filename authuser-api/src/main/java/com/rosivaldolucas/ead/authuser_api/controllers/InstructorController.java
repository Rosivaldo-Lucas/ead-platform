package com.rosivaldolucas.ead.authuser_api.controllers;

import com.rosivaldolucas.ead.authuser_api.dtos.InstructorDTO;
import com.rosivaldolucas.ead.authuser_api.enums.UserType;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/instructors")
public class InstructorController {

  @Autowired
  private UserService userService;

  @PostMapping("/subscription")
  public ResponseEntity<?> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {
    Optional<User> userOptional = this.userService.findById(instructorDTO.getUserId());

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    User user = userOptional.get();
    user.setUserType(UserType.INSTRUCTOR);
    user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
    this.userService.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

}
