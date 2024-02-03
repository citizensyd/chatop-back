/*package com.chatapp.backend.configuration;

import com.chatapp.backend.exceptions.TokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Ici, vous pouvez directement gérer les cas d'authentification manquante
        if (authException.getCause() instanceof TokenNotFoundException) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is missing.");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
*/