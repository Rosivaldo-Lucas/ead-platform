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
import java.util.List;
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

    @GetMapping("/courses/{idCourse}/modules")
    public ResponseEntity<?> getAllModulesIntoCourse(@PathVariable UUID idCourse, SpecificationTemplate.ModuleSpec spec, @PageableDefault(sort = "moduleId", direction = Sort.Direction.ASC)Pageable pageable) {
        Optional<Course> courseOptional = this.courseService.findById(idCourse);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Page<Module> allModulesIntoCourse = this.moduleService.findAllModulesIntoCourse(SpecificationTemplate.moduleCourseId(idCourse).and(spec), pageable);

        return ResponseEntity.ok(allModulesIntoCourse);
    }

    @GetMapping("/courses/{idCourse}/modules/{idModule}")
    public ResponseEntity<?> getOneModuleIntoCourse(@PathVariable UUID idCourse, @PathVariable UUID idModule) {
        Optional<Module> moduleIntoCourse = this.moduleService.findModuleIntoCourse(idCourse, idModule);

        if (moduleIntoCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }

        return ResponseEntity.ok(moduleIntoCourse.get());
    }

    @PostMapping("/courses/{idCourse}/modules")
    public ResponseEntity<?> saveModule(@PathVariable UUID idCourse, @RequestBody @Valid ModuleDTO moduleDTO) {
        log.debug("POST saveModule moduleDTO received {}", moduleDTO.toString());

        Optional<Course> courseOptional = this.courseService.findById(idCourse);

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

    @PutMapping("/courses/{idCourse}/modules/{idModule}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID idCourse, @PathVariable UUID idModule, @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<Module> moduleOptional = this.moduleService.findModuleIntoCourse(idCourse, idModule);

        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();

            BeanUtils.copyProperties(moduleDTO, module, "id");

            this.moduleService.save(module);

            return ResponseEntity.status(HttpStatus.OK).body("Module updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }
    }

    @DeleteMapping("/courses/{idCource}/modules/{idModule}")
    public ResponseEntity<?> deleteModule(@PathVariable UUID idCource, @PathVariable UUID idModule) {
        Optional<Module> moduleIntoCourse = this.moduleService.findModuleIntoCourse(idCource, idModule);

        if (moduleIntoCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }

        this.moduleService.delete(moduleIntoCourse.get());

        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
    }

}
