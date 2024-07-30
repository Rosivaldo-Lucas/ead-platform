package com.rosivaldolucas.ead.course_api.controllers;

import com.rosivaldolucas.ead.course_api.dtos.CourseDTO;
import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.services.CourseService;
import com.rosivaldolucas.ead.course_api.specifications.SpecificationTemplate;
import com.rosivaldolucas.ead.course_api.validation.CourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private CourseValidator courseValidator;

  @Autowired
  private CourseService courseService;

  @GetMapping
  public ResponseEntity<Page<Course>> getAllCourses(
          @RequestParam(required = false) UUID userId,
          SpecificationTemplate.CourseSpec spec,
          @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC)Pageable pageable
  ) {
    return ResponseEntity.ok(courseService.findAll(spec, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findOneCourse(@PathVariable UUID id) {
    Optional<Course> courseOptional = courseService.findById(id);

    if (courseOptional.isPresent()) {
      return ResponseEntity.ok(courseOptional.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }
  }

  @PostMapping
  public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO, Errors errors) {
    this.courseValidator.validate(courseDTO, errors);

    if (errors.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
    }

    log.debug("POST saveCourse courseDTO received {}", courseDTO.toString());

    Course newCourse = new Course();
    BeanUtils.copyProperties(courseDTO, newCourse);

    newCourse.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
    newCourse.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

    this.courseService.save(newCourse);

    log.debug("POST saveCourse idCourse saved {}", newCourse.getId());
    log.info("POST saveCourse course saved successfully idCourse {}", newCourse.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCourse(@PathVariable UUID id, @RequestBody @Valid CourseDTO courseDTO) {
    Optional<Course> courseOptional = this.courseService.findById(id);

    if (courseOptional.isPresent()) {
      Course course = courseOptional.get();

      BeanUtils.copyProperties(courseDTO, course, "id");
      course.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

      this.courseService.save(course);

      return ResponseEntity.status(HttpStatus.OK).body("Course updated successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourse(@PathVariable UUID id) {
    Optional<Course> courseOptional = this.courseService.findById(id);

    if (courseOptional.isPresent()) {
      this.courseService.delete(courseOptional.get());

      return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }
  }

}
