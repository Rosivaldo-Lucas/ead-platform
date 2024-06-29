package com.rosivaldolucas.ead.course_api.validation;

import com.rosivaldolucas.ead.course_api.clients.AuthUserClient;
import com.rosivaldolucas.ead.course_api.dtos.CourseDTO;
import com.rosivaldolucas.ead.course_api.dtos.UserDTO;
import com.rosivaldolucas.ead.course_api.enums.UserType;
import com.rosivaldolucas.ead.course_api.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Objects;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

  @Autowired
  @Qualifier("defaultValidator")
  private Validator validator;

  @Autowired
  private AuthUserClient authUserClient;

  @Override
  public boolean supports(@NonNull Class<?> aClass) {
    return false;
  }

  @Override
  public void validate(@NonNull Object o, @NonNull Errors errors) {
    CourseDTO course = (CourseDTO) o;

    this.validator.validate(course, errors);

    if (!errors.hasErrors()) {
      this.validateUserInstructor(course.getUserInstructor(), errors);
    }
  }

  private void validateUserInstructor(UUID userInstructor, Errors errors) {
    ResponseEntity<UserDTO> responseUserInstructor;

    try {
      responseUserInstructor = this.authUserClient.getOneUserById(userInstructor);

      if (Objects.requireNonNull(responseUserInstructor.getBody()).getUserType().equals(UserType.STUDENT)) {
        errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
      }
    } catch (HttpStatusCodeException ex) {
      if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
        errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found");
      }
    }
  }
}
