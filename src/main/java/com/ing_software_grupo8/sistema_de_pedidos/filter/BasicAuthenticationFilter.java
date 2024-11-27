package com.ing_software_grupo8.sistema_de_pedidos.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ing_software_grupo8.sistema_de_pedidos.service.IBasicService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    private final IBasicService basicService;
    private final FilterException exception;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String path = request.getRequestURI();
            if (!path.equals("/auth/login")) {
                filterChain.doFilter(request, response);
                return;
            }
            String email, password;
            try {
                email = basicService.getEmailFromToken(request);
                if (email == null) {
                    exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                    return;
                }

                password = basicService.getPasswordFromRequest(request);
                if (password == null) {
                    exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                    return;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }

            catch (IllegalArgumentException e) {
                exception.write(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exception.write(response, HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado");
        }
    }

}