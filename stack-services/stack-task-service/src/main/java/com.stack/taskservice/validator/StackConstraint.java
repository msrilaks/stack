package com.stack.taskservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StackValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StackConstraint {
    String message() default "Invalid Stack";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
