package com.rosivaldolucas.ead.authuser_api.dtos;

import java.util.UUID;

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

}
