package com.example.ase_project.login;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.example.ase_project.login.helper.ELoginEndpoints;
import com.example.ase_project.login.repository.IMyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class LoginConfiguration {

    private final IMyUserRepository myUserRepository;

    @Autowired
    public LoginConfiguration(IMyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    // TODO: use this everywhere in the code
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AuthenticationManager authenticationManager() {
        return authentication ->
                new UsernamePasswordAuthenticationToken(authentication.getName(),
                        authentication.getCredentials());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AntPathRequestMatcher loginMatcher = antMatcher(ELoginEndpoints.LOGIN.getHttpMethod(),
                ELoginEndpoints.LOGIN.getFullEndpoint());
        AntPathRequestMatcher registerMatcher = antMatcher(ELoginEndpoints.REGISTER.getHttpMethod(),
                ELoginEndpoints.REGISTER.getFullEndpoint());

        return http.authorizeHttpRequests(
                        authorize -> {
                            try {
                                authorize
                                        .requestMatchers(loginMatcher).permitAll()
                                        .requestMatchers(registerMatcher).permitAll()
                                        .anyRequest()
                                        .permitAll()//.anyRequest().authenticated() TODO: ANY REQUEST
                                        .and()
                                        .addFilter(new JwtAuthenticationFilter(authenticationManager(),
                                                myUserRepository))
                                        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                                        .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                // we are creating a service used by non browser clients and thus we can disable csrf
                //  (see: https://docs.spring.io/spring-security/reference/features/exploits/csrf.html#csrf-when)
                .csrf().disable()
                .build();
    }
}
