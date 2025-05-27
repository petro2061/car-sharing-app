package carsharingapp.app.service.user;

import carsharingapp.app.dto.user.UpdateUserRoleRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto updateUserRole(Long id, UpdateUserRoleRequestDto updateUserRoleRequestDto);
}
