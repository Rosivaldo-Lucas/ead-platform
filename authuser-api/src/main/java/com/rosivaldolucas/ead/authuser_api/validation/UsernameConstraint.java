package com.rosivaldolucas.ead.authuser_api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {

    String message() default "{username.validation.constraints.username}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
