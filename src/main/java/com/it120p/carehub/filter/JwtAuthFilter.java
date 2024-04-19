package com.it120p.carehub.filter;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        // Check if Authorization header exists and is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get token and username
        String jwtTokenString = authorizationHeader.substring(7);
        String jwtUsername = jwtService.extractUsername(jwtTokenString);

        if (jwtUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtUsername);

            // Check if userDetails is null
            if (userDetails == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // Check if user is not yet activated
            User user = (User) userDetails;
            if (!user.isUserActivated()) {
                filterChain.doFilter(request, response);
                return;
            }

            // Check if token is valid and allowed
            if (jwtService.isTokenAllowedByUser(userDetails.getUsername(), jwtTokenString)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(jwtTokenString);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
