package carsharingapp.app.dto.user;

import carsharingapp.app.enums.RoleType;

public record UserResponseDto(
        String id,
        String email,
        String firstName,
        String lastName,
        RoleType role
) {
}
