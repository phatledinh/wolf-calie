package com.phatle.wolf_calie.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.phatle.wolf_calie.exception.InvalidTokenException;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new InvalidTokenException("Xác thực thất bại hoặc token không hợp lệ");
        }
        return (Jwt) authentication.getPrincipal();
    }

    @Override
    public Long getCurrentUserId() {
        Jwt jwt = getJwt();
        Object claim = jwt.getClaim("userId");
        if (claim == null) {
            throw new InvalidTokenException("Token thiếu userId claim");
        }
        if (claim instanceof Number number) {
            return number.longValue();
        } else if (claim instanceof String str) {
            try {
                return Long.valueOf(str);
            } catch (NumberFormatException e) {
                throw new InvalidTokenException("Token userId claim không hợp lệ (không phải số)");
            }
        }
        throw new InvalidTokenException("Token userId claim không đúng định dạng");
    }

    @Override
    public String getCurrentUserEmail() {
        Jwt jwt = getJwt();
        String email = jwt.getSubject();
        if (email == null || email.isBlank()) {
            throw new InvalidTokenException("Token thiếu subject (email) claim");
        }
        return email;
    }
}
