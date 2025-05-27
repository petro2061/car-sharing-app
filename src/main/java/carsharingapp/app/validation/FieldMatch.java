package carsharingapp.app.validation;

import carsharingapp.app.validation.impl.FieldMatchValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldMatchValidation.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "Repeated password does not match the entered one. "
            + "Please check the correctness of the entered data!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();
    String secondField();
}
