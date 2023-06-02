package com.ase.login;

import static org.mockito.Mockito.when;

import com.ase.login.data.ERole;
import com.ase.login.data.IUserUnmodifiable;
import com.ase.login.exception.JWTInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class JWTHelperTests {

    private final String userId = "some-id";
    private final String email = "some@email.com";
    private final String password = "somepassword";
    private final ERole role = ERole.ATTENDEE;

    private final int expiration_time = 1000;
    private final String secret = "test-secret";
    private JWTHelper jwtHelper;

    private IUserUnmodifiable getTestUser() {
        return getTestUser(userId, email, password, role);
    }

    private IUserUnmodifiable getTestUser(String userId, String email, String password,
            ERole role) {
        IUserUnmodifiable user = Mockito.mock(IUserUnmodifiable.class);
        when(user.getId()).thenReturn(userId);
        when(user.getEmail()).thenReturn(email);
        when(user.getRole()).thenReturn(role);
        when(user.getPassword()).thenReturn(password);

        return user;
    }

    @BeforeEach
    public void setJwtHelper() {
        jwtHelper = new JWTHelper(expiration_time, secret);
    }

    @Test
    public void JWTHelper_generateJWTResponse_validCall() {
        IUserUnmodifiable user = getTestUser();
        JWTResponse response = jwtHelper.generateJWTResponse(user);
        Assertions.assertNotNull(response);

        Assertions.assertEquals(response.user(), user);
    }

    @Test
    public void JWTHelper_verifyToken_immediately() {
        IUserUnmodifiable user = getTestUser();
        JWTResponse response = jwtHelper.generateJWTResponse(user);

        Assertions.assertTrue(jwtHelper.verifyToken(response.jwt()));
    }

    @Test
    public void JWTHelper_verifyToken_letExpire() {
        IUserUnmodifiable user = getTestUser();
        JWTResponse response = jwtHelper.generateJWTResponse(user);
        long start = System.currentTimeMillis();

        try {
            Thread.sleep(expiration_time + 1, 0);
        } catch (IllegalMonitorStateException | InterruptedException e) {
            Assertions.fail();
        }

        long end = System.currentTimeMillis();
        Assertions.assertTrue((end - start) > expiration_time);
        Assertions.assertFalse(jwtHelper.verifyToken(response.jwt()));
    }

    @Test
    public void JWTHelper_extractId_validCall() {
        IUserUnmodifiable user = getTestUser();
        JWTResponse response = jwtHelper.generateJWTResponse(user);

        try {
            String extractedId = jwtHelper.extractId(response.jwt());
            Assertions.assertEquals(userId, extractedId);
        } catch (JWTInvalidException e) {
            Assertions.fail();
        }
    }

    @Test
    public void JWTHelper_extractId_emptyId() {
        String emptyId = "";
        IUserUnmodifiable user = getTestUser(emptyId, email, password, role);
        JWTResponse response = jwtHelper.generateJWTResponse(user);

        try {
            String extractedId = jwtHelper.extractId(response.jwt());
            Assertions.assertEquals(emptyId, extractedId);
        } catch (JWTInvalidException e) {
            Assertions.fail();
        }
    }

    @Test
    public void JWTHelper_extractId_expiredToken() {
        IUserUnmodifiable user = getTestUser();
        JWTResponse response = jwtHelper.generateJWTResponse(user);

        try {
            Thread.sleep(expiration_time + 1, 0);
        } catch (IllegalMonitorStateException | InterruptedException e) {
            Assertions.fail();
        }

        Executable exec = () -> jwtHelper.extractId(response.jwt());
        Assertions.assertThrows(Exception.class, exec); // TODO: custom exception class
    }
}
