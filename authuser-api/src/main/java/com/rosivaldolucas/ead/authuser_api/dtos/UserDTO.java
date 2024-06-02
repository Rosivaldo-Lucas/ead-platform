package com.rosivaldolucas.ead.authuser_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;

    private String fullName;

    private String cpf;

    private String email;

    private String phoneNumber;

    private String username;

    private String password;

    private String oldPassword;

    private String imageUrl;

}
