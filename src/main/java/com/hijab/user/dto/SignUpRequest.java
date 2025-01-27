package com.hijab.user.dto;

import com.hijab.user.annotation.PasswordAndIdCheck;
import com.hijab.user.annotation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@PasswordMatches
@PasswordAndIdCheck(userEmail = "email", password = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class SignUpRequest {

    // 정규식이 틀렸을때 발생하는 예외 MethodArgumentNotValidException
    @Email
    private final String email;

    @Pattern(regexp = "[a-zA-Z0-9]{4,8}$", message = "닉네임은 최소 4자 이상, 8자 이하이며, 영문과 숫자만 입력하세요.")
    private final String nickname;

    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])^[a-zA-Z0-9~!@#$%^&*()+|=]{6,30}$", message = "비밀번호는 최소 6자 이상, 30자 이하이며, 영문 대소문자, 숫자, 특수문자룰 포함하여 입력하세요.")
    private final String password;

    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])^[a-zA-Z0-9~!@#$%^&*()+|=]{6,30}$", message = "비밀번호를 확인해주세요.")
    private final String matchingPassword;

    @Builder
    public SignUpRequest(String email, String nickname, String password, String matchingPassword) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
