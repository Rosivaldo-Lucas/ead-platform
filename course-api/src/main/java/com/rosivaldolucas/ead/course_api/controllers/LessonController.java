package com.rosivaldolucas.ead.course_api.controllers;

import com.rosivaldolucas.ead.course_api.dtos.LessonDto;
import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.models.Module;
import com.rosivaldolucas.ead.course_api.services.LessonService;
import com.rosivaldolucas.ead.course_api.services.ModuleService;
import com.rosivaldolucas.ead.course_api.specifications.SpecificationTemplate;
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

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @GetMapping("/modules/{idModule}/lessons")
    public ResponseEntity<Page<Lesson>> getAllLessons(@PathVariable UUID idModule, SpecificationTemplate.LessonSpec spec, @PageableDefault(sort = "idLesson", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(idModule).and(spec), pageable));
    }

    @GetMapping("/modules/{idModule}/lessons/{idLesson}")
    public ResponseEntity<Object> getOneLesson(@PathVariable UUID idModule, @PathVariable UUID idLesson) {
        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(idModule, idLesson);

        if (lessonModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
    }

    @PostMapping("/modules/{idModule}/lessons")
    public ResponseEntity<?> saveLesson(@PathVariable UUID idModule, @RequestBody @Valid LessonDto lessonDto) {
        Optional<Module> moduleModelOptional = moduleService.findById(idModule);

        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found.");
        }

        var lessonModel = new Lesson();
        BeanUtils.copyProperties(lessonDto, lessonModel);
        lessonModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
    }

    @PutMapping("/modules/{idModule}/lessons/{idLesson}")
    public ResponseEntity<Object> updateLesson(@PathVariable UUID idModule, @PathVariable UUID idLesson, @RequestBody @Valid LessonDto lessonDto) {
        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(idModule, idLesson);

        if (lessonModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        var lessonModel = lessonModelOptional.get();
        lessonModel.setTitle(lessonDto.getTitle());
        lessonModel.setDescription(lessonDto.getDescription());
        lessonModel.setVideoUrl(lessonDto.getVideoUrl());

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
    }

    @DeleteMapping("/modules/{idModule}/lessons/{idLesson}")
    public ResponseEntity<?> deleteLesson(@PathVariable UUID idModule, @PathVariable UUID idLesson){
        Optional<Lesson> lessonModelOptional = lessonService.findLessonIntoModule(idModule, idLesson);

        if(lessonModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        lessonService.delete(lessonModelOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully.");
    }

}
