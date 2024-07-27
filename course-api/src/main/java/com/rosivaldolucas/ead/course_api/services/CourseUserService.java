package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.clients.AuthUserClient;
import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.CourseUser;
import com.rosivaldolucas.ead.course_api.repositories.CourseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserService {

  @Autowired
  private CourseUserRepository courseUserRepository;

  @Autowired
  private AuthUserClient authUserClient;

  public boolean existsByCourseAndUserId(Course course, UUID userId) {
    return this.courseUserRepository.existsByCourseAndUserId(course, userId);
  }

  public CourseUser save(CourseUser courseUser) {
    return this.courseUserRepository.save(courseUser);
  }

  @Transactional
  public CourseUser saveAndSendSubscriptionUserInCourse(CourseUser courseUser) {
    courseUser = this.courseUserRepository.save(courseUser);

    this.authUserClient.postSubscriptionUserInCourse(courseUser.getCourse().getId(), courseUser.getUserId());

    return courseUser;
  }

}
