package carsharingapp.app.dto.user;

import carsharingapp.app.validation.ValidRole;
import jakarta.validation.constraints.NotEmpty;

public record UpdateUserRoleRequestDto(
        @NotEmpty(message = "Role list must not be empty")
        @ValidRole
        String role
) {
}
