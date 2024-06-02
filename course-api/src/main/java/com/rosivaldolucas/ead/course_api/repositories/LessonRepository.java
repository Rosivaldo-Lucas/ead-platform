package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    @Query(value = "SELECT * FROM LESSON WHERE ID_MODULE = :idModule", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("idModule") UUID idModule);

}
