package carsharingapp.app.controller;

import carsharingapp.app.dto.user.UpdateUserRoleRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.dto.user.UserUpdateRequestDto;
import carsharingapp.app.model.User;
import carsharingapp.app.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/role")
    UserResponseDto updateRole(@PathVariable @Positive Long id,
                               @RequestBody @Valid UpdateUserRoleRequestDto roleRequestDto) {
        return userService.updateUserRole(id, roleRequestDto);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @GetMapping("/me")
    UserResponseDto getCurrentUser(Authentication authentication) {
        return userService.getById(getIdForAuthenticationUser(authentication));
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER')")
    @PutMapping("/me")
    UserResponseDto update(Authentication authentication,
                           @RequestBody @Valid UserUpdateRequestDto updateRequestDto) {
        return userService
                .update(getIdForAuthenticationUser(authentication), updateRequestDto);
    }

    private Long getIdForAuthenticationUser(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }
}
