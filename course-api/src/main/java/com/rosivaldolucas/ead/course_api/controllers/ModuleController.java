package com.rosivaldolucas.ead.course_api.controllers;

import com.rosivaldolucas.ead.course_api.dtos.ModuleDTO;
import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.Module;
import com.rosivaldolucas.ead.course_api.services.CourseService;
import com.rosivaldolucas.ead.course_api.services.ModuleService;
import com.rosivaldolucas.ead.course_api.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> getAllModulesIntoCourse(@PathVariable UUID courseId, SpecificationTemplate.ModuleSpec spec, @PageableDefault(sort = "moduleId", direction = Sort.Direction.ASC)Pageable pageable) {
        Optional<Course> courseOptional = this.courseService.findById(courseId);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Page<Module> allModulesIntoCourse = this.moduleService.findAllModulesIntoCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);

        return ResponseEntity.ok(allModulesIntoCourse);
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> getOneModuleIntoCourse(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        Optional<Module> moduleIntoCourse = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleIntoCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }

        return ResponseEntity.ok(moduleIntoCourse.get());
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<?> saveModule(@PathVariable UUID courseId, @RequestBody @Valid ModuleDTO moduleDTO) {
        log.debug("POST saveModule moduleDTO received {}", moduleDTO.toString());

        Optional<Course> courseOptional = this.courseService.findById(courseId);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Module newModule = new Module();
        BeanUtils.copyProperties(moduleDTO, newModule);

        newModule.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        newModule.setCourse(courseOptional.get());

        this.moduleService.save(newModule);

        log.debug("POST saveModule idModule saved {}", newModule.getId());
        log.info("POST saveModule course saved successfully idModule {}", newModule.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(newModule);
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID courseId, @PathVariable UUID moduleId, @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<Module> moduleOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();

            BeanUtils.copyProperties(moduleDTO, module, "id");

            this.moduleService.save(module);

            return ResponseEntity.status(HttpStatus.OK).body("Module updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        Optional<Module> moduleIntoCourse = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleIntoCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }

        this.moduleService.delete(moduleIntoCourse.get());

        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
    }

}
