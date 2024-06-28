package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndIdUser(Course course, UUID idUser);

}
