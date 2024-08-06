package com.rosivaldolucas.ead.course_api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rosivaldolucas.ead.course_api.enums.CourseLevel;
import com.rosivaldolucas.ead.course_api.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "course_tb")
public class Course implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;

  @Column(nullable = false, length = 300)
  private String description;

  @Column
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CourseStatus courseStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CourseLevel courseLevel;

  @Column(nullable = false)
  private UUID userInstructor;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private Set<Module> modules = new HashSet<>();

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "course_user_tb",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private LocalDateTime createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private LocalDateTime updatedAt;

}
