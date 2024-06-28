package com.rosivaldolucas.ead.authuser_api.repositories;

import com.rosivaldolucas.ead.authuser_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);

}
