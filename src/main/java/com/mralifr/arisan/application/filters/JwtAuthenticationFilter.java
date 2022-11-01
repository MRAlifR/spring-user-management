package com.mralifr.arisan.application.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mralifr.arisan.application.models.requests.AuthUserRequest;
import com.mralifr.arisan.application.models.responses.TokenResponse;
import com.mralifr.arisan.helpers.JwtTokenHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelpers jwtTokenHelpers;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenHelpers jwtTokenHelpers) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtTokenHelpers = jwtTokenHelpers;
        setFilterProcessesUrl("/v1/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";
        try {
            AuthUserRequest creds = new ObjectMapper().readValue(request.getInputStream(), AuthUserRequest.class);
            username = creds.getUsername();
            password = creds.getPassword();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessToken = jwtTokenHelpers.generateToken(
                (String) authResult.getPrincipal(),
                authResult
                        .getAuthorities()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet())
        );
        TokenResponse token = TokenResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .expiresIn(jwtTokenHelpers.getExpiredTimeInMinute() * 60)
                .scope("Arisan")
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(token));
        response.getWriter().flush();
    }
}
