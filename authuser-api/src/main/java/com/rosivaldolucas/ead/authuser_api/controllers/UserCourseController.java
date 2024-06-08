package com.rosivaldolucas.ead.authuser_api.controllers;

import com.rosivaldolucas.ead.authuser_api.clients.UserClient;
import com.rosivaldolucas.ead.authuser_api.dtos.CourseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserCourseController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCoursesByUser(
            @PathVariable UUID userId,
            @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userClient.getAllCoursesByUser(userId, pageable));
    }

}
