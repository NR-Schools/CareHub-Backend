package com.it120p.carehub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.JwtService;


@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) throws AuthenticationException {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

            String token = authorizationHeader.substring(7);
            String jwtUsername = jwtService.extractUsername(token);

            if (jwtUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtUsername);

                // Check if userDetails is null
                if (userDetails == null) {
                    throw new AuthenticationException("User cannot be found") {};
                }

                // Check if user is not yet activated
                User user = (User) userDetails;
                if (!user.isUserActivated()) {
                    throw new AuthenticationException("User is not activated") {};
                }

                // Check if token is valid and allowed
                if (jwtService.isTokenAllowedByUser(userDetails.getUsername(), token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(token);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    accessor.setUser(authToken);
                }
            }
        
            System.out.println("authorizationHeader");
        }

        return message;
    }
}
