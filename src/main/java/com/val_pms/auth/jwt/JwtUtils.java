package com.val_pms.auth.jwt;

import com.val_pms.auth.model.User;
import com.val_pms.auth.model.UserDetailsImpl;
import com.val_pms.auth.service.UserEntityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Autowired
    UserEntityService userEntityService;

    @Value("${val_pms.app.jwtSecret}")
    private String jwtSecret;

    @Value("${val_pms.app.jwtExpirationMs}")
    private int jwtExpirationsMs;

    @Value("${val_pms.app.jwtCookieName}")
    private String jwtCookieName;

    @Value("${val_pms.app.jwtRefreshCookieName}")
    private String jwtRefreshCookieName;

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateJwtTokenFromUsername(userPrincipal.getUsername());
        return generateCookie(jwtCookieName, jwt, "/api");
    }

    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateJwtTokenFromUsername(user.getUsername());
        return generateCookie(jwtCookieName, jwt, "/api");
    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookieName, refreshToken, "/api/auth/refresh");
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookieName);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookieName);
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName).path("/api").build();
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return ResponseCookie.from(jwtRefreshCookieName).path("/api/auth/refresh_token").build();
    }

    public String generateJwtTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationsMs))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    TODO: in the method above signWith is deprecated. Try to use this method instead of explicit
//    specifying jwtSecret
    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie
                .from(name, value)
                .path(path)
                //24 hours or 1 day
                .maxAge(60 * 60 * 24L)
                .httpOnly(true).build();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(generateKey())
                    .build().parse(authToken, new JwtHandlerAdapter<String>() {
                @Override
                public String onClaimsJws(Jws<Claims> jws) {
                    return jws.getBody().getSubject();
                }
            });
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String getCookieValueByName(HttpServletRequest request, String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
