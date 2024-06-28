package com.rosivaldolucas.ead.authuser_api.dtos;

import com.rosivaldolucas.ead.authuser_api.enums.CourseLevel;
import com.rosivaldolucas.ead.authuser_api.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDTO {

  private UUID id;

  private String name;

  private String description;

  private String imageUrl;

  private CourseStatus courseStatus;

  private CourseLevel courseLevel;

  private UUID userInstructor;

}
