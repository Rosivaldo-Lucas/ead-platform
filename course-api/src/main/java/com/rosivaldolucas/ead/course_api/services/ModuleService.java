package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.models.Module;
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
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public Page<Module> findAllModulesIntoCourse(Specification<Module> specification, Pageable pageable) {
        return this.moduleRepository.findAll(specification, pageable);
    }

    public List<Module> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    public Optional<Module> findById(UUID id) {
        return this.moduleRepository.findById(id);
    }

    public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return this.moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    public void save(Module module) {
        moduleRepository.save(module);
    }

    @Transactional
    public void delete(Module module) {
        List<Lesson> lessons = this.lessonRepository.findAllLessonsIntoModule(module.getId());

        if (!lessons.isEmpty()) {
            this.lessonRepository.deleteAll(lessons);
        }

        this.moduleRepository.delete(module);
    }

}
