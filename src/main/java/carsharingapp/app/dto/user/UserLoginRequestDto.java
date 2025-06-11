package carsharingapp.app.dto.user;

import carsharingapp.app.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @Email
        @NotBlank(message = "Field email can't be null")
        String email,
        @Length(min = 4, max = 35)
        @Password
        String password
) {
}
