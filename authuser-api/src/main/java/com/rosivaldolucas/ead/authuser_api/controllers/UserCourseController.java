package com.rosivaldolucas.ead.authuser_api.controllers;

import com.rosivaldolucas.ead.authuser_api.clients.CourseClient;
import com.rosivaldolucas.ead.authuser_api.dtos.CourseDTO;
import com.rosivaldolucas.ead.authuser_api.dtos.UserCourseDTO;
import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.models.UserCourse;
import com.rosivaldolucas.ead.authuser_api.services.UserCourseService;
import com.rosivaldolucas.ead.authuser_api.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserCourseController {

  @Autowired
  private CourseClient courseClient;

  @Autowired
  private UserService userService;

  @Autowired
  private UserCourseService userCourseService;

  @GetMapping("/users/{userId}/courses")
  public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(
          @PathVariable UUID userId,
          @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.courseClient.getAllCoursesByUser(userId, pageable));
  }

  @PostMapping("/users/{userId}/courses/subscription")
  public ResponseEntity<?> saveSubscriptionUserInCourse(
          @PathVariable UUID userId,
          @RequestBody @Valid UserCourseDTO userCourseDTO
  ) {
    Optional<User> userOptional = this.userService.findById(userId);

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    if (this.userCourseService.existsByUserAndCourseId(userOptional.get(), userCourseDTO.getCourseId())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");
    }

    UserCourse userCourse = this.userCourseService.save(userOptional.get().convertToUserCourse(userCourseDTO.getCourseId()));

    return ResponseEntity.status(HttpStatus.CREATED).body(userCourse);
  }

}
