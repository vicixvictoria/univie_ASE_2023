package com.example.ase_project.login;

import static com.example.ase_project.login.helper.LoginConstants.SECRET;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Based on: <a
 * href="https://erinc.io/2020/08/02/spring-jwt-authentication-and-authorization/">source</a>
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String HEADER = "Authorization";
    private static final String HEADER_BEARER = "Bearer ";

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    private static String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(HEADER_BEARER, ""))
                .getSubject();
    }

    private static String extractAuthenticationHeader(HttpServletRequest req) {
        return req.getHeader(HEADER);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        String header = extractAuthenticationHeader(req);

        if (header == null || !header.startsWith(HEADER_BEARER)) {
            // if the header is invalid or does not contain the bearer token, do default filter chain and return
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {

        String header = extractAuthenticationHeader(req);
        if (header == null) {
            return null;
        }

        String user = extractUsername(header);
        if (user == null) {
            return null;
        }

        // new arraylist means authorities
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

    }
}
