package carsharingapp.app.service.user.impl;

import carsharingapp.app.dto.user.UpdateUserRoleRequestDto;
import carsharingapp.app.dto.user.UserRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.dto.user.UserUpdateRequestDto;
import carsharingapp.app.enums.RoleType;
import carsharingapp.app.exception.EntityNotFoundException;
import carsharingapp.app.exception.RegistrationException;
import carsharingapp.app.mapper.UserMapper;
import carsharingapp.app.model.Role;
import carsharingapp.app.model.User;
import carsharingapp.app.repository.RoleRepository;
import carsharingapp.app.repository.UserRepository;
import carsharingapp.app.service.user.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String BASE_ROLE_FOR_NEW_USER = "CUSTOMER";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserResponseDto userRegistration(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RegistrationException("Can't registration user. User with email: "
                    + userRequestDto.getEmail()
                    + " already exist");
        }
        Role role = findRoleByType(BASE_ROLE_FOR_NEW_USER);
        User user = userMapper.toUserModel(userRequestDto);

        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRoles(Set.of(role));

        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto update(Long id, UserUpdateRequestDto updateUser) {
        User user = findUserById(id);

        if (!updateUser.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(updateUser.getEmail())) {
            throw new IllegalArgumentException("Email: "
                    + updateUser.getEmail()
                    + "is already taken");

        }
        user.setEmail(updateUser.getEmail());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());

        if (updateUser.getPassword() != null && !updateUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userMapper.toResponseDto(findUserById(id));
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
