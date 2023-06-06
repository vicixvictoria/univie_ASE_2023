package com.ase.login.dataAccess;

import com.ase.login.domain.IUserUnmodifiable;
import com.ase.login.exception.JWTInvalidException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Responsible for helping with tasks related to JWT tokens.
 * <p>
 * TODO: could potentially benefit from a dedicated JWT class
 */
@Component
public class JWTHelper {

    private final int EXPIRATION_TIME_MS;

    private final String SECRET;

    public JWTHelper(@Value("${jwt.expiration-time-ms}") int expiration_time_ms,
            @Value("${jwt.secret}") String secret) {
        this.EXPIRATION_TIME_MS = expiration_time_ms;
        this.SECRET = secret;
    }

    /**
     * Generates a JWT given the user
     *
     * @param user the user for which the JWT is generate
     * @return a String, the JWT
     */
    public String generateJWT(IUserUnmodifiable user) {
        return JWT.create()
                .withSubject(user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    /**
     * Generates a {@link JWTResponse} (a data object containing the JWT, userId and user role)
     * given a user object
     *
     * @param user the user object from which the JWT is generated
     * @return the JWTResponse
     */
    public JWTResponse generateJWTResponse(IUserUnmodifiable user) {
        String token = generateJWT(user);
        return new JWTResponse(user, token);
    }

    /**
     * Returns whetehr the given JWT is valid or not
     *
     * @param token the to be checked token
     * @return boolean, whether the token is valid
     */
    public boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Extracts the userId given a token
     *
     * @param token the JWT from which the userId will be extracted
     * @return the userId of the owner of the JWT
     * @throws JWTInvalidException thrown if the given JWT is invalid
     */
    public String extractId(String token) throws JWTInvalidException {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTInvalidException("The provided token could not be validated!");
        }
    }


}
