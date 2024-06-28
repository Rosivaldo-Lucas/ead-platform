package com.rosivaldolucas.ead.course_api.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CourseUserDTO {

  private UUID courseId;
  private UUID userId;

}
