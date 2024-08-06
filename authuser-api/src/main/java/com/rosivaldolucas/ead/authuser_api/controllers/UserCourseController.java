package com.rosivaldolucas.ead.authuser_api.controllers;

import com.rosivaldolucas.ead.authuser_api.clients.CourseClient;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserCourseController {

  @Autowired
  private CourseClient courseClient;

  @Autowired
  private UserService userService;

  @GetMapping("/users/{userId}/courses")
  public ResponseEntity<?> getAllCoursesByUser(
          @PathVariable UUID userId,
          @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable
  ) {

    Optional<User> userOptional = this.userService.findById(userId);

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(this.courseClient.getAllCoursesByUser(userId, pageable));
  }

}
