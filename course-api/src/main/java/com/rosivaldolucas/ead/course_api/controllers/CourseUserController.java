package com.rosivaldolucas.ead.course_api.controllers;

import com.rosivaldolucas.ead.course_api.clients.CourseClient;
import com.rosivaldolucas.ead.course_api.dtos.SubscriptionDTO;
import com.rosivaldolucas.ead.course_api.dtos.UserDTO;
import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.CourseUser;
import com.rosivaldolucas.ead.course_api.services.CourseService;
import com.rosivaldolucas.ead.course_api.services.CourseUserService;
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
public class CourseUserController {

  @Autowired
  private CourseClient courseClient;

  @Autowired
  private CourseService courseService;

  @Autowired
  private CourseUserService courseUserService;

  @PostMapping("/courses/{courseId}/users/subscription")
  public ResponseEntity<?> saveSubscriptionInCourse(
          @PathVariable UUID courseId,
          @RequestBody @Valid SubscriptionDTO subscriptionDTO
  ) {
    Optional<Course> courseOptional = this.courseService.findById(courseId);

    if (courseOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }

    if (this.courseUserService.existsByCourseAndUserId(courseOptional.get(), subscriptionDTO.getUserId())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Subscription already exists");
    }

    // TODO: verificação user

    CourseUser courseUser = courseUserService.save(courseOptional.get().converterToCourseUser(subscriptionDTO.getUserId()));

    return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully");
  }

  @GetMapping("/courses/{courseId}/users")
  public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(
          @PathVariable UUID courseId,
          @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(this.courseClient.getAllUsersByCourse(courseId, pageable));
  }

}
