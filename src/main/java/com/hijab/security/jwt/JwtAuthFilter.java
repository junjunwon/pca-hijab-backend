package com.hijab.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hijab.security.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken(request);
        if (token != null) {
            if (!jwtProvider.validateToken(token)) {
                response.sendError(401, "만료되었습니다.");
                jwtExceptionHandler(response, "401", HttpStatus.BAD_REQUEST.value());
                return;
            }
            Claims userInfo = jwtProvider.getUserInfoFromToken(token);
            setAuthentication(userInfo.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtProvider.createUserAuthentication(email);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }


    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

