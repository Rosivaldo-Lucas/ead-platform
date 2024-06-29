package com.rosivaldolucas.ead.authuser_api.repositories;

import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

  @Query(value = "select * from tb_users_courses where user_user_id = :userId", nativeQuery = true)
  List<UserCourse> findAllUserCourseIntoUser(@Param("userId") UUID userId);

  boolean existsByUserAndIdCourse(User user, UUID idCourse);

}
