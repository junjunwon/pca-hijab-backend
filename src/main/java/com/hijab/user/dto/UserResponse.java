package com.hijab.user.dto;

import com.hijab.user.entity.User;
import com.hijab.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class UserResponse {

    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRoleEnum role;

    private UserResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}
