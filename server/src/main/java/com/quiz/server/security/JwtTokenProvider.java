package com.quiz.server.security;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.model.entity.User;
import com.quiz.server.properties.AppProperties;
import io.jsonwebtoken.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.quiz.server.exception.ExceptionCode.E007;
import static com.quiz.server.exception.ExceptionCode.E008;
import static com.quiz.server.security.SecurityConstants.EXPIRE_DURATION;
import static com.quiz.server.security.SecurityConstants.TOKEN_PREFIX;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenProvider {

    @Autowired
    private AppProperties appProperties;

    public String generateToken(User user, boolean keepLogged) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, appProperties.getJwt().getSecretAsBytes());

        if (keepLogged)
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION));

        return jwtBuilder.compact();
    }

    public Claims parseToken(@NotNull String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(appProperties.getJwt().getSecretAsBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new QRuntimeException(E007, e);
        } catch (SignatureException e) {
            throw new QRuntimeException(E008, e);
        }
    }
}
