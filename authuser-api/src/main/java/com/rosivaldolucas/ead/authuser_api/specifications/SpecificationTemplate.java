package com.rosivaldolucas.ead.authuser_api.specifications;

import com.rosivaldolucas.ead.authuser_api.models.User;
import com.rosivaldolucas.ead.authuser_api.models.UserCourse;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "fullName", spec = Like.class),
            @Spec(path = "email", spec = Like.class)
    })
    public interface UserSpecification extends Specification<User> { }

    public static Specification<User> userIdCourse(UUID idCourse) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<User, UserCourse> userJoinUserCourse = root.join("userCourses");

            return cb.equal(userJoinUserCourse.get("idCourse"), idCourse);
        };
    }

}
