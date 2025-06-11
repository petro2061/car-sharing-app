package carsharingapp.app.controller;

import carsharingapp.app.dto.user.UserLoginRequestDto;
import carsharingapp.app.dto.user.UserLoginResponseDto;
import carsharingapp.app.dto.user.UserRequestDto;
import carsharingapp.app.dto.user.UserResponseDto;
import carsharingapp.app.security.AuthenticationService;
import carsharingapp.app.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    UserResponseDto registration(@RequestBody @Valid UserRequestDto requestDto) {
        return userService.userRegistration(requestDto);
    }

    @PostMapping("/authenticate")
    UserLoginResponseDto authenticate(@RequestBody @Valid UserLoginRequestDto loginRequestDto) {
        return authenticationService.authenticate(loginRequestDto);
    }
}
