package com.rosivaldolucas.ead.authuser_api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rosivaldolucas.ead.authuser_api.dtos.UserEventDTO;
import com.rosivaldolucas.ead.authuser_api.enums.UserStatus;
import com.rosivaldolucas.ead.authuser_api.enums.UserType;
import lombok.Data;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_tb")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, length = 150)
  private String fullName;

  @Column(nullable = false, length = 20)
  private String cpf;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(length = 20)
  private String phoneNumber;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  @Column
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus userStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserType userType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  public UserEventDTO convertToUserEventDTO() {
    UserEventDTO userEventDTO = new UserEventDTO();
    
    BeanUtils.copyProperties(this, userEventDTO);

    userEventDTO.setUserType(this.getUserType().toString());
    userEventDTO.setUserStatus(this.getUserStatus().toString());

    return userEventDTO;
  }

}
