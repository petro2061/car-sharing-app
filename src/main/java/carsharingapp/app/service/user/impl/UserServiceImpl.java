package carsharingapp.app.service.user.impl;

import carsharingapp.app.dto.user.UpdateUserRoleRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.enums.RoleType;
import carsharingapp.app.exception.EntityNotFoundException;
import carsharingapp.app.mapper.UserMapper;
import carsharingapp.app.model.Role;
import carsharingapp.app.model.User;
import carsharingapp.app.repository.RoleRepository;
import carsharingapp.app.repository.UserRepository;
import carsharingapp.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto updateUserRole(Long userId,
                                          UpdateUserRoleRequestDto updateRole) {
        User user = findUserById(userId);
        Role role = findRoleByType(updateRole.role());

        user.getRoles().clear();
        user.getRoles().add(role);

        return userMapper.toResponseDto(userRepository.save(user));
    }

    private Role findRoleByType(String role) {
        try {
            return roleRepository.findByRoleType(RoleType.valueOf(role.toUpperCase()))
                    .orElseThrow(() ->
                            new EntityNotFoundException("Role not found in DB: " + role));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + userId));
    }
}
