package com.ing_software_grupo8.sistema_de_pedidos.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
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
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

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
                    writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                    return;
                }

                password = basicService.getPasswordFromRequest(request);
                if (password == null) {
                    writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                    return;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }

            catch (IllegalArgumentException e) {
                writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
                return;
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                processAuthentication(request, response, filterChain, email, password);
                return;
            }

            writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
        } catch (Exception e) {
            writeException(response, HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado");
        }
    }

    private void processAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain, String email, String password) throws IOException, ServletException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        authenticateUser(request, userDetails);
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void writeException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        ApiException apiException = new ApiException(status, message);
        responseMap.put("statusCode", apiException.getStatusCode().value());
        responseMap.put("message", apiException.getMessage());
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}