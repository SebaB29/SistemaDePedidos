package com.ing_software_grupo8.sistema_de_pedidos.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ing_software_grupo8.sistema_de_pedidos.service.IJwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final FilterException exception;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            if (path.startsWith("/auth") || path.startsWith("/h2-console")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = jwtService.getTokenFromRequest(request);
            if (token == null) {
                exception.write(response, HttpStatus.UNAUTHORIZED, "El token no fue enviado");
                return;
            }
            if (jwtService.isTokenExpired(token)) {
                exception.write(response, HttpStatus.FORBIDDEN, "El token expiro");
                return;
            }
            String email = jwtService.getEmailFromToken(token);
            if (email == null) {
                exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }
            filterChain.doFilter(request, response);
        }

        catch (SignatureException e) {
            exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
        } catch (MalformedJwtException e) {
            exception.write(response, HttpStatus.UNAUTHORIZED, "El token esta mal formado");
        } catch (UnsupportedJwtException e) {
            exception.write(response, HttpStatus.UNAUTHORIZED,
                    "El algoritmo de firma del token no es soportado por el sistema");
        } catch (ExpiredJwtException e) {
            exception.write(response, HttpStatus.FORBIDDEN, "El token expiro");
        } catch (Exception e) {
            exception.write(response, HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado");
        }
    }
}