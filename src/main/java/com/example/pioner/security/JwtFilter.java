package com.example.pioner.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
        throws ServletException, IOException
    {
        String token = extractToken(request);

        if (token != null && JwtUtil.validateToken(token))
        {
            Claims claims = JwtUtil.extractClaims(token);
            String userId = claims.getSubject();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userId, null, Collections.emptyList()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            //todo delete
            System.out.println("JwtFilter: userId: " + userId);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(final HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }
}
