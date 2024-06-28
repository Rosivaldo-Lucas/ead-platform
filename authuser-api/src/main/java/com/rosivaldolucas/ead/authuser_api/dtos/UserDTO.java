package com.rosivaldolucas.ead.authuser_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.rosivaldolucas.ead.authuser_api.validation.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

  @NotBlank(groups = UserView.RegistrationPost.class)
  @Email(groups = UserView.RegistrationPost.class)
  @JsonView(UserView.RegistrationPost.class)
  private String email;

  @JsonView({ UserView.RegistrationPost.class, UserView.UserPut.class })
  private String phoneNumber;

  @NotBlank(groups = UserView.RegistrationPost.class)
  @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
  @UsernameConstraint(groups = UserView.RegistrationPost.class)
  @JsonView(UserView.RegistrationPost.class)
  private String username;

  @NotBlank(groups = { UserView.RegistrationPost.class, UserView.PasswordChangePut.class })
  @Size(min = 6, max = 20, groups = { UserView.RegistrationPost.class, UserView.PasswordChangePut.class })
  @JsonView({ UserView.RegistrationPost.class, UserView.PasswordChangePut.class })
  private String password;

  @NotBlank(groups = UserView.PasswordChangePut.class)
  @Size(min = 6, max = 20, groups = UserView.PasswordChangePut.class)
  @JsonView(UserView.PasswordChangePut.class)
  private String oldPassword;

  @NotBlank(groups = UserView.ImageChangePut.class)
  @JsonView(UserView.ImageChangePut.class)
  private String imageUrl;

}
