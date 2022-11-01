package com.mralifr.arisan.infrastructure.configs;

import com.mralifr.arisan.application.filters.ExceptionHandlerFilter;
import com.mralifr.arisan.application.filters.JwtAuthenticationFilter;
import com.mralifr.arisan.application.filters.JwtAuthorizationFilter;
import com.mralifr.arisan.domain.ports.inputs.UserInputPort;
import com.mralifr.arisan.helpers.JwtTokenHelpers;
import com.mralifr.arisan.infrastructure.adapters.AuthenticationManagerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, JwtTokenHelpers jwtTokenHelpers, UserInputPort userInputPort) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(new ExceptionHandlerFilter(resolver), JwtAuthenticationFilter.class);
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(userInputPort), jwtTokenHelpers));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(userInputPort), jwtTokenHelpers));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserInputPort userInputPort) throws Exception {
        return new AuthenticationManagerAdapter(userInputPort, passwordEncoder());
    }

}
