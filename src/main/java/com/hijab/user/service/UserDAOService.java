package com.hijab.user.service;

import com.hijab.user.entity.User;

public interface UserDAOService {
    User getByEmail(String email);
    void validateByNickname(String nickname);
    void validateByEmail(String email);
}
