package com.rosivaldolucas.ead.course_api.services;

import com.rosivaldolucas.ead.course_api.models.User;
import com.rosivaldolucas.ead.course_api.repositories.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Page<User> findAll(Specification<User> spec, Pageable pageable) {
    return this.userRepository.findAll(spec, pageable);
  }

  public User save(User user) {
    return this.userRepository.save(user);
  }

  public void delete(UUID userId) {
    this.userRepository.deleteById(userId);
  }

}
