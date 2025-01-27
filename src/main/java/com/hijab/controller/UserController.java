package com.hijab.controller;

import com.hijab.security.dto.TokenResponse;
import com.hijab.security.jwt.JwtProvider;
import com.hijab.user.dto.CustomUserDetails;
import com.hijab.user.dto.LoginRequest;
import com.hijab.user.dto.SignUpRequest;
import com.hijab.user.service.UserServiceImpl;
import com.hijab.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final JwtProvider jwtProvider;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody @Valid SignUpRequest signUpRequest) {
        userServiceImpl.signup(signUpRequest);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        UserResponse user = userServiceImpl.login(loginRequest);
        TokenResponse token = jwtProvider.createTokenByLogin(user.getEmail(), user.getRole());
        response.addHeader(JwtProvider.AUTHORIZATION_HEADER, token.getAccessToken());

        return token;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    public ResponseEntity<CustomUserDetails> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}