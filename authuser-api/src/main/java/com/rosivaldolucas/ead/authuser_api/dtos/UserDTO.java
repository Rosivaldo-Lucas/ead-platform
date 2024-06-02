package com.rosivaldolucas.ead.authuser_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView {
        interface RegistrationPost { }
        interface UserPut { }
        interface PasswordChangePut { }
        interface ImageChangePut { }
    }

    private UUID id;

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    private String fullName;

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    private String cpf;

    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
    private String phoneNumber;

    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @JsonView({ UserView.RegistrationPost.class, UserView.PasswordChangePut.class })
    private String password;

    @JsonView(UserView.PasswordChangePut.class)
    private String oldPassword;

    @JsonView(UserView.ImageChangePut.class)
    private String imageUrl;

}
