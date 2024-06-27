package com.rosivaldolucas.ead.course_api.dtos;

import com.rosivaldolucas.ead.course_api.enums.UserStatus;
import com.rosivaldolucas.ead.course_api.enums.UserType;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID userId;
    private String username;
    private String fullName;
    private String cpf;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private UserStatus userStatus;
    private UserType userType;

}
