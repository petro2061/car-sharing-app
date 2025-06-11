package carsharingapp.app.dto.user;

import carsharingapp.app.validation.FieldMatch;
import carsharingapp.app.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(firstField = "password", secondField = "repeatPassword")
public class UserUpdateRequestDto {
    @Email
    @NotBlank(message = "email must not be blank")
    private String email;
    @NotBlank(message = "first name must not be blank")
    @Size(min = 1, max = 50)
    private String firstName;
    @NotBlank(message = "last name must not be blank")
    @Size(min = 1, max = 50)
    private String lastName;
    @Password
    @Length(min = 4, max = 35)
    private String password;
    @Length(min = 4, max = 35)
    private String repeatPassword;
}
