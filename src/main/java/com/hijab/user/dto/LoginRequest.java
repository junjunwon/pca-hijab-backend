package com.hijab.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class LoginRequest {

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
