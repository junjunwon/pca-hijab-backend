package com.hijab.security.jwt;

import com.hijab.security.dto.TokenResponse;
import com.hijab.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 1000 * 60 * 30L * 48; // 30 분 * 48 = 1일
    private static final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 7L;// 7일

    @Value("${jwt.secret.key}")
    private String secretKey;

    //HMAC-SHA 키를 생성
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    private final UserDetailsService userDetailsService;

    // 이 코드는 HMAC-SHA 키를 생성하는 데 사용되는 Base64 인코딩된 문자열을 디코딩하여 키를 초기화하는 용도로 사용
    @PostConstruct//의존성 주입이 이루어진 후 초기화를 수행하는 어노테이션
    public void init() {
        byte[] bytes = Base64.getDecoder()
            .decode(secretKey);// Base64로 인코딩된 값을 시크릿키 변수에 저장한 값을 디코딩하여 바이트 배열로 변환
        //* Base64 (64진법) : 바이너리(2진) 데이터를 문자 코드에 영향을 받지 않는 공통 ASCII문자로 표현하기 위해 만들어진 인코딩
        key = Keys.hmacShaKeyFor(
            bytes);//디코팅된 바이트 배열을 기반으로 HMAC-SHA 알고리즘을 사용해서 Key객체로 반환 , 이를 key 변수에 대입
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String createToken(String email, UserRoleEnum role, Long tokenExpireTime) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
            .claim(AUTHORIZATION_KEY, role)
            .setSubject(email)
            .setIssuedAt(date)
            .setExpiration(new Date(date.getTime() + tokenExpireTime))
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    public TokenResponse createTokenByLogin(String email, UserRoleEnum role) {
        String accessToken = createToken(email, role, ACCESS_TOKEN_TIME);
        String refreshToken = createToken(email, role, REFRESH_TOKEN_TIME);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse reissueAccessToken(String email, UserRoleEnum role, String reToken) {
        String accessToken = createToken(email, role, ACCESS_TOKEN_TIME);
        String refreshToken = createToken(email, role, REFRESH_TOKEN_TIME);
        return new TokenResponse(accessToken, refreshToken);
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
            log.info("Invalid JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty");
        }
        return false;
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(accessToken)
            .getBody()
            .getExpiration();
        return (expiration.getTime() - new Date().getTime());
    }

    public Authentication createUserAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

}

