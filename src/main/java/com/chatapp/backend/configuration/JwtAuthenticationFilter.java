package com.chatapp.backend.configuration;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.chatapp.backend.DTO.CustomErrorResponse;
import com.chatapp.backend.exceptions.JwtAuthenticationException;
import com.chatapp.backend.exceptions.TokenNotFoundException;
import com.chatapp.backend.services.JWTservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
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

    private final ObjectMapper objectMapper;


    /**
     * Extracts the JWT from the Authorization header.
     *
     * @param authHeader The Authorization header value.
     * @return The extracted JWT.
     * @throws IllegalArgumentException If the Authorization header is invalid or missing.
     */
    private String extractJwtFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            if (authHeader.length() > 7) {
                return authHeader.substring(7);
            } else {
                throw new IllegalArgumentException("Invalid Authorization header");
            }
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
    }


    /**
     * This method loads the user details based on the provided user email.
     *
     * @param userEmail The email of the user whose details are to be loaded.
     * @return The user details.
     */
    private UserDetails loadUserDetails(String userEmail) {
        return this.userDetailsService.loadUserByUsername(userEmail);
    }

    /**
     * This constant variable represents a list of whitelisted paths that should not be filtered by
     * the JwtAuthenticationFilter.
     *
     * The paths are represented by URI patterns. The filter will check if the request URI starts
     * with any of the whitelisted paths in this array. If it does, the request will not be filtered
     * and will be allowed to proceed.
     *
     * Example:
     *
     *    "/api/auth/**" - Matches any URI that starts with "/api/auth/"
     *    "/api/auth/register" - Matches exact URI "/api/auth/register"
     *    "/api/auth/login" - Matches exact URI "/api/auth/login"
     *    "/swagger-ui/" - Matches exact URI "/swagger-ui/"
     *    "/v3/api-docs" - Matches exact URI "/v3/api-docs"
     */
    private static final String[] WHITELISTED_PATHS = {
            "/api/auth/**",
            "/api/auth/register",
            "/api/auth/login",
            "/swagger-ui/",
            "/v3/api-docs",
    };

    /**
     * Determines whether the given request should not be filtered.
     *
     * @param request The HTTP request.
     * @return True if the request URI starts with any of the whitelisted paths specified in the WHITELISTED_PATHS array, false otherwise.
     * @throws ServletException If an error occurs while processing the request.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(WHITELISTED_PATHS).anyMatch(request.getRequestURI()::startsWith);
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @return The JSON string representation of the object.
     * @throws IOException If an error occurs while converting the object to JSON.
     */
    private String convertObjectToJson(Object object) throws IOException {
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
            if (authHeader != null) {
                if (authHeader.startsWith("Bearer ")) {
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
                                handleJwtError(response, HttpStatus.BAD_REQUEST, "Bad request, user not retrieve with jwt provided");
                                return;
                            }
                        }
                    } catch (TokenExpiredException e) {
                        handleJwtError(response, HttpStatus.UNAUTHORIZED, "Token expired.");
                        return;
                    } catch (MalformedJwtException e) {
                        handleJwtError(response, HttpStatus.UNAUTHORIZED, "Malformed token.");
                        return;
                    }
                } else {
                    handleJwtError(response, HttpStatus.BAD_REQUEST, "Invalid Authorization header: Missing 'Bearer' prefix");
                    return;
                }
            } else {
                handleJwtError(response, HttpStatus.BAD_REQUEST, "Authorization header is missing");
                return;
            }
        } catch (Exception e) {
            handleJwtError(response, HttpStatus.UNAUTHORIZED, "Invalid token.");
            return;
        }
        filterChain.doFilter(request, response);
    }



    private void handleJwtError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(status, message);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
    }


}
