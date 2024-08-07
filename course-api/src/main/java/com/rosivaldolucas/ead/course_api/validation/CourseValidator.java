package com.rosivaldolucas.ead.course_api.validation;

import com.rosivaldolucas.ead.course_api.dtos.CourseDTO;
import com.rosivaldolucas.ead.course_api.enums.UserType;
import com.rosivaldolucas.ead.course_api.models.User;
import com.rosivaldolucas.ead.course_api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

  @Autowired
  @Qualifier("defaultValidator")
  private Validator validator;

  @Autowired
  private UserService userService;

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
    Optional<User> userOptional = this.userService.findById(userInstructor);

    if (userOptional.isEmpty()) {
      errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found");
    }

    User user = userOptional.get();

    if (user.getUserType().equals(UserType.STUDENT.toString())) {
      errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
    }
  }
}
