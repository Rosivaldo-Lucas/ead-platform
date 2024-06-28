package com.rosivaldolucas.ead.authuser_api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rosivaldolucas.ead.authuser_api.dtos.UserDTO;
import com.rosivaldolucas.ead.authuser_api.enums.UserStatus;
import com.rosivaldolucas.ead.authuser_api.enums.UserType;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private UserService userService;

  @GetMapping
  public String index() {
    // NIVEIS DE LOGS
    log.trace("TRACE");
    log.debug("DEBUG");
    log.info("INFO");
    log.warn("WARN");
    log.error("ERROR");

    try {
      throw new RuntimeException("RuntimeException");
    } catch (Exception ex) {
      log.error("ERROR", ex);
    }

    return "Logging Spring Boot...";
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(
          @RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO
  ) {
    log.debug("POST signup userDTO received: {}", userDTO.toString());

    if (this.userService.existsByUsername(userDTO.getUsername())) {
      log.warn("POST signup username {} is Already Taken", userDTO.getUsername());

      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
    }

    if (this.userService.existsByEmail(userDTO.getEmail())) {
      log.warn("POST signup email {} is Already Taken", userDTO.getEmail());

      return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
    }

    User newUser = new User();
    BeanUtils.copyProperties(userDTO, newUser);
    newUser.setUserStatus(UserStatus.ACTIVE);
    newUser.setUserType(UserType.STUDENT);
    newUser.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
    newUser.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

    this.userService.save(newUser);

    log.debug("POST signup idUser saved: {}", newUser.getId());
    log.info("POST signup user successfully idUser: {}", newUser.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
  }

}
