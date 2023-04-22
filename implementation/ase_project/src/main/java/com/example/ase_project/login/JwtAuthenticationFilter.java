package com.example.ase_project.login;

import static com.example.ase_project.login.helper.LoginConstants.EXPIRATION_TIME_MS;
import static com.example.ase_project.login.helper.LoginConstants.SECRET;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ase_project.login.data.MyUserData;
import com.example.ase_project.login.exception.UserNotFoundException;
import com.example.ase_project.login.helper.ELoginEndpoints;
import com.example.ase_project.login.repository.IMyUserRepository;
import com.example.ase_project.login.repository.MyUserSavable;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Based on: <a
 * href="https://erinc.io/2020/08/02/spring-jwt-authentication-and-authorization/">source</a>
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final IMyUserRepository myUserRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
            IMyUserRepository myUserRepository) {
        this.authenticationManager = authenticationManager;
        this.myUserRepository = myUserRepository;

        setFilterProcessesUrl(ELoginEndpoints.LOGIN.getFullEndpoint());
    }

    private static String generateJWT(Authentication auth) {
        return JWT.create()
                .withSubject((String) auth.getPrincipal())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    private static String generateBody(Authentication auth, String token) {
        String username = (String) auth.getPrincipal();
        return username + " " + token;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
            HttpServletResponse res) throws RuntimeException {
        try {
            MyUserData user = new ObjectMapper().readValue(req.getInputStream(), MyUserData.class);

            // TODO: check if the repository contains the user and if the passwords match
            try {
                Optional<MyUserSavable> queryResult = myUserRepository.findMyUserSavableByEmail(
                        user.getEmail());

                if (queryResult.isEmpty()) {
                    throw new UsernameNotFoundException("bla bla");
                }

                MyUserSavable foundUser = queryResult.get();

                if (!foundUser.getPassword().equals(user.getPassword())) {
                    throw new UsernameNotFoundException("bla bla");
                }
            } catch (UsernameNotFoundException e) {
                throw new UserNotFoundException(e.getMessage());
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword(),
                            new ArrayList<>()) // TODO: how do I fill this list?
            );
        } catch (IOException | UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException {
        String token = generateJWT(auth);
        String body = generateBody(auth, token);
        res.getWriter().write(body);
        res.getWriter().flush();
    }

}