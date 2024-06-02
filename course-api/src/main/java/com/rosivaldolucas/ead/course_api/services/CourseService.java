package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.models.Module;
import com.rosivaldolucas.ead.course_api.repositories.CourseRepository;
import com.rosivaldolucas.ead.course_api.repositories.LessonRepository;
import com.rosivaldolucas.ead.course_api.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

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
