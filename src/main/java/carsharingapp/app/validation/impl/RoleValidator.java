package carsharingapp.app.validation.impl;

import carsharingapp.app.enums.RoleType;
import carsharingapp.app.validation.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String fullRoleName = "ROLE_" + value.toUpperCase();

        return Arrays.stream(RoleType.values())
                .map(Enum::name)
                .anyMatch(valid -> valid.equals(fullRoleName));
    }
}
