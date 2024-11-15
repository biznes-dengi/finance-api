package com.finance.app.config;

import com.finance.app.boundary.request.ValidationRequest;
import com.finance.app.process.AuthProcess;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    private static final String[] AUTH_WHITELIST = {
            "/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Component
    @RequiredArgsConstructor
    public static class JwtAuthenticationFilter extends OncePerRequestFilter {

        private static final List<AntPathRequestMatcher> MATCHERS = Arrays.stream(AUTH_WHITELIST)
                .map(AntPathRequestMatcher::new)
                .toList();

        private final SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
        private final AuthProcess service;

        @Override
        protected boolean shouldNotFilter(final HttpServletRequest request) {
            return MATCHERS.stream().anyMatch(matcher -> matcher.matches(request));
        }

        @Override
        protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                        final FilterChain filterChain) {
            Optional.ofNullable(getTokenFromRequest(request))
                    .map(token -> service.validateToken(new ValidationRequest(token)))
                    .ifPresentOrElse(validationResponse -> {
                        final var auth = new UsernamePasswordAuthenticationToken(validationResponse.email(), null, List.of());
                        strategy.getContext().setAuthentication(auth);
                        doFilter(request, response, filterChain);
                    }, () -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
        }

        private String getTokenFromRequest(final HttpServletRequest request) {
            return Optional.ofNullable(request.getHeader("Authorization"))
                    .filter(token -> token.startsWith("Bearer "))
                    .map(token -> token.substring(7))
                    .orElse(null);
        }

        @SneakyThrows
        private void doFilter(final HttpServletRequest request, final HttpServletResponse response,
                              final FilterChain filterChain) {
            filterChain.doFilter(request, response);
        }
    }
}
