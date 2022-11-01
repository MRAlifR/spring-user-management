package com.mralifr.arisan.infrastructure.adapters;

import com.mralifr.arisan.domain.models.User;
import com.mralifr.arisan.domain.ports.inputs.UserInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationManagerAdapter implements AuthenticationManager {

    private final UserInputPort userInputPort;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManagerAdapter(UserInputPort userInputPort, PasswordEncoder passwordEncoder) {
        this.userInputPort = userInputPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userInputPort.getUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        List<GrantedAuthority> roles = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.toString())
        ).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }
}
