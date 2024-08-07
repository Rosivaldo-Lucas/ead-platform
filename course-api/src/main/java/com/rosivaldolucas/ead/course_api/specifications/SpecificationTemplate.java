package com.rosivaldolucas.ead.course_api.specifications;

import com.rosivaldolucas.ead.course_api.models.Course;
import com.rosivaldolucas.ead.course_api.models.Lesson;
import com.rosivaldolucas.ead.course_api.models.Module;
import com.rosivaldolucas.ead.course_api.models.User;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<Course> { }

    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpec extends Specification<Module> { }

    @Spec(path = "title", spec = Like.class)
    public interface LessonSpec extends Specification<Lesson> { }

    @And({
        @Spec(path = "email", spec = Like.class),
        @Spec(path = "fullName", spec = Like.class),
        @Spec(path = "userStatus", spec = Equal.class),
        @Spec(path = "userType", spec = Equal.class)
    })
    public interface UserSpec extends Specification<User> { }

    public static Specification<Module> moduleCourseId(UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);

            Expression<Collection<Module>> coursesModules = course.get("modules");

            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, coursesModules));
        };
    }

    public static Specification<Lesson> lessonModuleId(UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Root<Lesson> lesson = root;
            Root<Module> module = query.from(Module.class);

            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");

            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons));
        };
    }

    public static Specification<User> userCourseId(UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Root<User> user = root;
            Root<Course> course = query.from(Course.class);

            Expression<Collection<User>> courseUsers = course.get("users");

            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(user, courseUsers));
        };
    }

    public static Specification<Course> courseUserId(UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Root<Course> course = root;
            Root<User> user = query.from(User.class);

            Expression<Collection<Course>> userCourses = user.get("courses");

            return cb.and(cb.equal(course.get("userId"), userId), cb.isMember(course, userCourses));
        };
    }

}
