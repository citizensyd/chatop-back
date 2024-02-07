package com.chatapp.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    //  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] NO_AUTHENTICATION = {
            "/api/auth/**",
            "/api/auth/login",
            "/static/images/**",
            "/api/auth/register",
            "/authenticate",
            "/swagger-resources/",
            "/swagger-ui/",
            "/v3/api-docs/",
            "/api/v1/app/user/auth/",
            "/v3/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs",
            "/swagger.html",
            "/swagger",
            "/swagger-ui-custom.html",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui/index.html",
            "/swagger-config/**",
            "/v3/api-docs/**",
            "/v3/api-docs/swagger-config",
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(NO_AUTHENTICATION);
    }
    /**
     * Creates and configures a SecurityFilterChain object for HTTP security.
     *
     * @param http The HttpSecurity object used to configure security for requests.
     * @return The created SecurityFilterChain object.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(NO_AUTHENTICATION)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider) /*
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )*/
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}