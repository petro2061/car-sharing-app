package carsharingapp.app.validation.impl;

import carsharingapp.app.validation.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidation implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.firstField();
        this.secondFieldName = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object firstValue
                = new BeanWrapperImpl(value).getPropertyValue(this.firstFieldName);
        Object secondValue =
                new BeanWrapperImpl(value).getPropertyValue(this.secondFieldName);
        return Objects.equals(firstValue, secondValue);
    }
}
