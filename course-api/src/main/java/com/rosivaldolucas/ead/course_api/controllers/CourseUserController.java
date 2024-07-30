package com.rosivaldolucas.ead.course_api.controllers;

import com.rosivaldolucas.ead.course_api.dtos.SubscriptionDTO;
import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.services.CourseService;
import com.rosivaldolucas.ead.course_api.services.UserService;
import com.rosivaldolucas.ead.course_api.specifications.SpecificationTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CourseUserController {

  @Autowired
  private CourseService courseService;

  @Autowired
  private UserService userService;

  @GetMapping("/courses/{courseId}/users")
  public ResponseEntity<?> getAllUsersByCourse(
          @PathVariable UUID courseId,
          SpecificationTemplate.UserSpec spec,
          @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable
  ) {
    Optional<Course> courseOptional = this.courseService.findById(courseId);

    if (courseOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
  }

  @PostMapping("/courses/{courseId}/users/subscription")
  public ResponseEntity<?> saveSubscriptionInCourse(
          @PathVariable UUID courseId,
          @RequestBody @Valid SubscriptionDTO subscriptionDTO
  ) {
    Optional<Course> courseOptional = this.courseService.findById(courseId);

    if (courseOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body("");
  }

}
