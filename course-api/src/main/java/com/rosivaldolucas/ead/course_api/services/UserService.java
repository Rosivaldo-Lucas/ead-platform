package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.User;
import com.rosivaldolucas.ead.course_api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  @Autowired
  private UserRepository courseUserRepository;

  public Page<User> findAll(Specification<User> spec, Pageable pageable) {
    return this.courseUserRepository.findAll(spec, pageable);
  }

}
