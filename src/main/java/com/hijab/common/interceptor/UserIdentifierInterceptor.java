package com.hijab.common.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class UserIdentifierInterceptor implements HandlerInterceptor {

    private static final String COOKIE_NAME = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        String userId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        // UUID가 없으면 새로 발급해서 쿠키에 저장
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(COOKIE_NAME, userId);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365); // 1년
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        // request attribute에 저장해서 어디서든 접근 가능
        request.setAttribute(COOKIE_NAME, userId);
        return true;
    }
}
