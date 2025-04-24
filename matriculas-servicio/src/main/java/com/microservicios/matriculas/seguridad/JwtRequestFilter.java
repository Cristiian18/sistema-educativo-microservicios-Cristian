package com.microservicios.matriculas.seguridad;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        log.info("===> [MATRICULAS] Procesando request en filtro JWT");
        log.info("===> Path: {}", request.getServletPath());

        // Imprimir todos los headers para debug
        Collections.list(request.getHeaderNames()).forEach(headerName -> {
            log.info("===> Header '{}': {}", headerName, request.getHeader(headerName));
        });

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            // Verifica si el token parece un JWT (debe tener al menos dos puntos)
            if (!token.contains(".")) {
                log.warn("===> Token no parece un JWT (no contiene puntos), ignorando.");
                chain.doFilter(request, response);
                return;
            }

            try {
                username = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes()) // usa getBytes() para evitar errores
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if (username != null) {
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_USER")
                    );

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, token, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("===> Autenticación establecida para usuario: {}", username);
                }
            } catch (Exception e) {
                log.error("===> Error al procesar token JWT: {}", e.getMessage());
            }
        } else {
            log.info("===> No se encontró token Bearer");
        }

        chain.doFilter(request, response);
    }
}
