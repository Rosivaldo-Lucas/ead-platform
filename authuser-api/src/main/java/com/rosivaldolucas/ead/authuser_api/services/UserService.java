package com.rosivaldolucas.ead.authuser_api.services;

import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll() {
    return this.userRepository.findAll();
  }

  public Page<User> findAll(Specification<User> specification, Pageable pageable) {
    return this.userRepository.findAll(specification, pageable);
  }

  public Optional<User> findById(UUID id) {
    return this.userRepository.findById(id);
  }

  public boolean existsByUsername(String username) {
    return this.userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return this.userRepository.existsByEmail(email);
  }

  public void save(User user) {
    this.userRepository.save(user);
  }

  @Transactional
  public void delete(User user) {
    this.userRepository.delete(user);
  }

}
