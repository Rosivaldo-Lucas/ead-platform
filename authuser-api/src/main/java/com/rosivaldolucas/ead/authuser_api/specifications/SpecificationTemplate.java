package com.rosivaldolucas.ead.authuser_api.specifications;

import com.rosivaldolucas.ead.authuser_api.models.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

  @And({
          @Spec(path = "userType", spec = Equal.class),
          @Spec(path = "userStatus", spec = Equal.class),
          @Spec(path = "fullName", spec = Like.class),
          @Spec(path = "email", spec = Like.class)
  })
  public interface UserSpecification extends Specification<User> { }

}
