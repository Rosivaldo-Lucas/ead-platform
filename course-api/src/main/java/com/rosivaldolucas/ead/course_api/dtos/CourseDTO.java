package com.rosivaldolucas.ead.course_api.dtos;

import com.rosivaldolucas.ead.course_api.enums.CourseLevel;
import com.rosivaldolucas.ead.course_api.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private CourseLevel courseLevel;

    @NotNull
    private UUID userInstructor;

}
