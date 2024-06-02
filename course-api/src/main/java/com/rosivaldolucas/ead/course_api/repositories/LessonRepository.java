package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
}
