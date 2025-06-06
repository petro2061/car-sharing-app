package carsharingapp.app.validation;

import carsharingapp.app.validation.impl.RoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Invalid role. Allowed values: CUSTOMER, MANAGER";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
