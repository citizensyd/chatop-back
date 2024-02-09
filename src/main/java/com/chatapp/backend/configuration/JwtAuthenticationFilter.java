package com.chatapp.backend.configuration;

import com.chatapp.backend.DTO.CustomErrorResponse;
import com.chatapp.backend.exceptions.JwtAuthenticationException;
import com.chatapp.backend.exceptions.TokenNotFoundException;
import com.chatapp.backend.services.JWTservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final JWTservice jwtService;


    private String extractJwtFromHeader(String authHeader) {
        return authHeader.substring(7);
    }

    private UserDetails loadUserDetails(String userEmail) {
        return this.userDetailsService.loadUserByUsername(userEmail);
    }

    private static final String[] WHITELISTED_PATHS = {
            "/api/auth/**",
            "/api/auth/register",
            "/images/**",
            "/api-docs",
            "/api/auth/login",
            "/swagger.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui-custom.html",
            "/swagger",
            "/authenticate",
            "/swagger-resources/",
            "/swagger-ui/",
            "/v3/api-docs/",
            "/api/v1/app/user/auth/",
            "/swagger.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-config/**",
            "/v3/api-docs/**",
            "/v3/api-docs/swagger-config"
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Utilisez les mêmes chemins que ceux définis dans AUTHENTICATION_NEEDED_ROUTES
        return Arrays.stream(WHITELISTED_PATHS).anyMatch(path::startsWith);
    }

    private String convertObjectToJson(Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = extractJwtFromHeader(authHeader);
                try {
                    String userEmail = jwtService.extractEmail(jwt);
                    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = loadUserDetails(userEmail);
                        if (jwtService.isTokenValid(jwt, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        } else {
                            handleJwtErrorBad(response, "Bad request, user not retrieve with jwt provided");
                            return;
                        }
                    }
                } catch (Exception e) {
                    handleJwtError(response, e.getMessage());
                    return;
                }
            } else if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                handleJwtErrorBad(response, "Token is missing.");
                return;
            }
        } catch (Exception e) {
            handleJwtError(response, "Invalid token.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void handleJwtErrorBad(HttpServletResponse response, String message) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
    }

    private void handleJwtError(HttpServletResponse response, String message) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED, message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
    }

}
