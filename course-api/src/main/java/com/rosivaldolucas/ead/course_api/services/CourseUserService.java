package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.CourseUser;
import com.rosivaldolucas.ead.course_api.repositories.CourseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserService {

  @Autowired
  private CourseUserRepository courseUserRepository;

  public boolean existsByCourseAndUserId(Course course, UUID userId) {
    return this.courseUserRepository.existsByCourseAndIdUser(course, userId);
  }

  public CourseUser save(CourseUser courseUser) {
    return this.courseUserRepository.save(courseUser);
  }

}
