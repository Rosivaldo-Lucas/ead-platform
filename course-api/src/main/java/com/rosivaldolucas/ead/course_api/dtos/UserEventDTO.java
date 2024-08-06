package com.rosivaldolucas.ead.course_api.dtos;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.rosivaldolucas.ead.course_api.models.User;

import lombok.Data;

@Data
public class UserEventDTO {
  
  private UUID id;

  private String fullName;

  private String cpf;

  private String email;

  private String phoneNumber;

  private String username;

  private String imageUrl;

  private String userStatus;

  private String userType;

  private String actionType;

  public User convertToUser() {
    User user = new User();
    BeanUtils.copyProperties(this, user);
    return user;
  }

}
