package com.rosivaldolucas.ead.authuser_api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.rosivaldolucas.ead.authuser_api.dtos.UserDTO;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import com.rosivaldolucas.ead.authuser_api.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<Page<User>> getAllUsers(
          SpecificationTemplate.UserSpecification userSpecification,
          @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
  ) {
    Page<User> userPage = this.userService.findAll(userSpecification, pageable);

    return ResponseEntity.status(HttpStatus.OK).body(userPage);
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
          @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO
  ) {
    log.debug("PUT updateUser userDTO received {}", userDTO.toString());

    Optional<User> userOptional = this.userService.findById(id);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setFullName(userDTO.getFullName());
      user.setEmail(userDTO.getEmail());
      user.setPhoneNumber(userDTO.getPhoneNumber());
      user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

      this.userService.updateUser(user);

      log.debug("PUT updateUser idUser updated {}", id);
      log.info("PUT updateUser user updated successfully idUser {}", id);

      return ResponseEntity.status(HttpStatus.OK).body(user);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<?> updatePassword(
          @PathVariable UUID id,
          @RequestBody @Validated(UserDTO.UserView.PasswordChangePut.class) @JsonView(UserDTO.UserView.PasswordChangePut.class) UserDTO userDTO
  ) {
    log.debug("PUT updatePassword userDTO received {}", userDTO.toString());

    Optional<User> userOptional = this.userService.findById(id);

    if (userOptional.isPresent()) {
      if (!userDTO.getOldPassword().equals(userOptional.get().getPassword())) {
        log.warn("PUT updatePassword Mismatched old passwords idUser {}", userDTO.getId());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Mismatched old passwords!");
      }

      User user = userOptional.get();
      user.setPassword(userDTO.getPassword());
      user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

      this.userService.updatePassword(user);

      log.debug("PUT updatePassword idUser updated {}", id);
      log.info("PUT updatePassword user updated successfully idUser {}", id);

      return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully!");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
  }

  @PutMapping("/{id}/image")
  public ResponseEntity<?> updateImage(
          @PathVariable UUID id,
          @RequestBody @Validated(UserDTO.UserView.ImageChangePut.class) @JsonView(UserDTO.UserView.ImageChangePut.class) UserDTO userDTO
  ) {
    log.debug("PUT updateImage userDTO received {}", userDTO.toString());

    Optional<User> userOptional = this.userService.findById(id);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setImageUrl(userDTO.getImageUrl());
      user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

      this.userService.updateUser(user);

      log.debug("PUT updateImage idUser updated {}", id);
      log.info("PUT updateImage user updated successfully idUser {}", id);

      return ResponseEntity.status(HttpStatus.OK).body(user);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
    log.debug("DELETE deleteUser idUser received: {}", id);

    Optional<User> userOptional = this.userService.findById(id);

    if (userOptional.isPresent()) {
      this.userService.deleteUser(userOptional.get());

      log.debug("DELETE deleteUser idUser deleted {}", id);
      log.info("DELETE deleteUser user deleted successfully idUser: {}", id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
  }

}
