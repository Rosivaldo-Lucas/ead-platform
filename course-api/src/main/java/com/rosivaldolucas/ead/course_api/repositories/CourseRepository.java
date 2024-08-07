package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {

  @Query(value = "SELECT CASE WHEN COUNT(cut) > 0 THEN true ELSE false END FROM course_user_tb cut WHERE cut.course_id = :courseId AND cut.user_id = :userId", nativeQuery = true)
  boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

  @Modifying
  @Query(value = "INSERT INTO course_user_tb VALUES (:courseId, :userId);", nativeQuery = true)
  void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

}
