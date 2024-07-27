package com.rosivaldolucas.ead.authuser_api.services;

import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.models.UserCourse;
import com.rosivaldolucas.ead.authuser_api.repositories.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseService {

  @Autowired
  private UserCourseRepository userCourseRepository;

  public boolean existsByUserAndCourseId(User user, UUID courseId) {
    return this.userCourseRepository.existsByUserAndCourseId(user, courseId);
  }

  public UserCourse save(UserCourse userCourse) {
    return this.userCourseRepository.save(userCourse);
  }

}
