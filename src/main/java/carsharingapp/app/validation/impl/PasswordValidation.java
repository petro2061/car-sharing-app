package carsharingapp.app.validation.impl;

import carsharingapp.app.validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidation implements ConstraintValidator<Password, String> {
    private static final String PASSWORD_VALID_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d).*$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null
                && Pattern.compile(PASSWORD_VALID_PATTERN).matcher(password).matches();
    }
}
