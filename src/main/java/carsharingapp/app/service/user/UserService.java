package carsharingapp.app.service.user;

import carsharingapp.app.dto.user.UpdateUserRoleRequestDto;
import carsharingapp.app.dto.user.UserRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.dto.user.UserUpdateRequestDto;

public interface UserService {
    UserResponseDto updateUserRole(Long id, UpdateUserRoleRequestDto updateUserRoleRequestDto);

    UserResponseDto userRegistration(UserRequestDto userRequestDto);

    UserResponseDto update(Long id, UserUpdateRequestDto updateUser);

    UserResponseDto getById(Long id);
}
