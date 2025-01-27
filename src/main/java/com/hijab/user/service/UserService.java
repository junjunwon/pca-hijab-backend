package com.hijab.user.service;

import com.hijab.user.dto.LoginRequest;
import com.hijab.user.dto.SignUpRequest;
import com.hijab.user.dto.UserResponse;

public interface UserService {
    void signup(SignUpRequest signUpRequest);
    UserResponse login(LoginRequest loginRequest);
}
