package com.ing_software_grupo8.sistema_de_pedidos.jwt;

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

import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.service.IJwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            if (path.startsWith("/auth") || path.startsWith("/h2-console")) {
                filterChain.doFilter(request, response);
                return;
            }
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            String token = jwtService.getTokenFromRequest(request);
            if (token == null) {
                writeException(response, HttpStatus.UNAUTHORIZED, "El token no fue enviado");
                return;
            }
            if (jwtService.isTokenExpired(token)) {
                writeException(response, HttpStatus.FORBIDDEN, "El token expiro");
                return;
            }
            String email = jwtService.getEmailFromToken(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                processAuthentication(request, response, filterChain, path, token, email);
                return;
            }

            writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
        } catch (Exception e) {
            writeException(response, HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado");
        }
    }

    private void processAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain,
            String path, String token, String email) throws IOException, ServletException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (jwtService.isTokenValid(token, userDetails)) {
            authenticateUser(request, userDetails);
            filterChain.doFilter(request, response);
            return;
        }
        writeException(response, HttpStatus.UNAUTHORIZED, "El token es invalido");
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