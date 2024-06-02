package com.rosivaldolucas.ead.course_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUserRepository, Long> {
}
