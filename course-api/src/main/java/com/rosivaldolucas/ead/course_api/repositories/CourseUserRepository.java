package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    @Query(value = "select * from course_user_tb where course_id = :courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    boolean existsByCourseAndUserId(Course course, UUID courseId);

}
