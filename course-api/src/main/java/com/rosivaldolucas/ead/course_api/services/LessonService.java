package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;

    public Page<Lesson> findAllByModule(Specification<Lesson> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

    public List<Lesson> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    public Lesson save(Lesson lessonModel) {
        return lessonRepository.save(lessonModel);
    }

    public void delete(Lesson lessonModel) {
        lessonRepository.delete(lessonModel);
    }

}
