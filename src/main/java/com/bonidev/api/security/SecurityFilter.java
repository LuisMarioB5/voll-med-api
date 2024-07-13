package com.bonidev.api.security;

import com.bonidev.api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");

            var username = tokenService.getSubject(token);
            if (username != null) {
                var usuario = usuarioRepository.findByLogin(username);

                var authUser = new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authUser);
            }
        }
            filterChain.doFilter(request, response);
    }
}
