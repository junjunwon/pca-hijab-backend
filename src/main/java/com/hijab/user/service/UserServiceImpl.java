package com.hijab.user.service;

import com.hijab.common.exception.CustomException;
import com.hijab.common.exception.ExceptionStatus;
import com.hijab.repository.jpa.UserRepository;
import com.hijab.user.dto.LoginRequest;
import com.hijab.user.dto.SignUpRequest;
import com.hijab.user.entity.User;
import com.hijab.user.entity.UserRoleEnum;
import com.hijab.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAOService userDAOService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String nickname = signUpRequest.getNickname();

        // 닉네임과 이메일 중복 체크
        userDAOService.validateByNickname(nickname);
        userDAOService.validateByEmail(email);

        // 사용자 생성 및 저장
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = User.builder()
                        .email(email)
                        .nickname(nickname)
                        .password(encodedPassword)
                        .role(UserRoleEnum.MEMBER)
                        .build();

        userRepository.save(user);
    }


    @Override
    @Transactional
    public UserResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        userDAOService.validateByEmail(email);
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new CustomException(ExceptionStatus.WRONG_EMAIL)
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ExceptionStatus.WRONG_PASSWORD);
        }
        return new UserResponse().of(user);
    }
}

