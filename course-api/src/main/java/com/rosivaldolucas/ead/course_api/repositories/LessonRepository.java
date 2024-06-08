package com.rosivaldolucas.ead.course_api.repositories;

import com.rosivaldolucas.ead.course_api.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID>, JpaSpecificationExecutor<Lesson> {

    @Query(value = "SELECT * FROM LESSON WHERE ID_MODULE = :idModule", nativeQuery = true)
    List<Lesson> findAllLessonsIntoModule(@Param("idModule") UUID idModule);

    @Query(value = "select * from tb_lessons where module_module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
    Optional<Lesson> findLessonIntoModule(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);

}
