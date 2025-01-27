package com.hijab.user.service;

import com.hijab.common.exception.CustomException;
import com.hijab.common.exception.ExceptionStatus;
import com.hijab.repository.jpa.UserRepository;
import com.hijab.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDAOServiceImpl implements UserDAOService {

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionStatus.WRONG_EMAIL));
    }

    @Override
    public void validateByNickname(String nickname) {
        userRepository.existsByNickname(nickname).orElseThrow(
                () -> new CustomException(ExceptionStatus.SIGNUP_WRONG_USERNAME));
    }

    @Override
    public void validateByEmail(String email) {
        userRepository.existsByEmail(email).orElseThrow(
                () -> new CustomException(ExceptionStatus.WRONG_EMAIL));
    }
}
