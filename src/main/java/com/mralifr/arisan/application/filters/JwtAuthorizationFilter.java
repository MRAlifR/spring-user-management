package com.mralifr.arisan.application.filters;

import com.mralifr.arisan.helpers.JwtTokenHelpers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenHelpers jwtTokenHelpers;

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenHelpers jwtTokenHelpers) {
        super(authenticationManager);
        this.jwtTokenHelpers = jwtTokenHelpers;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = request
                .getHeader("Authorization")
                .replace("Bearer ", "");

        Jws<Claims> claims = jwtTokenHelpers.parseToken(accessToken);
        String principal = claims.getBody().getAudience();
        List<String> roles = claims.getBody().get("roles", List.class);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
                )
        );
        chain.doFilter(request, response);
    }
}
