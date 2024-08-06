package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.models.Module;
import com.rosivaldolucas.ead.course_api.repositories.CourseRepository;
import com.rosivaldolucas.ead.course_api.repositories.LessonRepository;
import com.rosivaldolucas.ead.course_api.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public Page<Course> findAll(Specification<Course> spec, Pageable pageable) {
        return this.courseRepository.findAll(spec, pageable);
    }

    public Optional<Course> findById(UUID id) {
        return this.courseRepository.findById(id);
    }

    public void save(Course course) {
        this.courseRepository.save(course);
    }

    @Transactional
    public void delete(Course course) {
        List<Module> modules = this.moduleRepository.findAllModulesIntoCourse(course.getId());

        if (!modules.isEmpty()) {
            for (Module module : modules) {
                List<Lesson> lessons = this.lessonRepository.findAllLessonsIntoModule(module.getId());

                if (!lessons.isEmpty()) {
                    this.lessonRepository.deleteAll(lessons);
                }
            }

            this.moduleRepository.deleteAll(modules);
        }

        this.courseRepository.delete(course);
    }

}
