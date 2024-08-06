package com.rosivaldolucas.ead.course_api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_tb")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private UUID id;

  @Column(nullable = false, length = 150)
  private String fullName;

  @Column(nullable = false, length = 20)
  private String cpf;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column
  private String imageUrl;

  @Column(nullable = false)
  private String userStatus;

  @Column(nullable = false)
  private String userType;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
  private Set<Course> courses = new HashSet<>();

}
