package carsharingapp.app.dto.user;

import carsharingapp.app.enums.RoleType;
import java.util.Set;

public record UserResponseDto(
        String id,
        String email,
        String firstName,
        String lastName,
        Set<RoleType> roles
) {
}
