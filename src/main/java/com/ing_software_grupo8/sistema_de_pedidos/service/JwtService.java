package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private static final long ACCESS_EXPIRED = 1000 * 60 * 24;
    private static final long REFRESH_EXPIRED = 1000L * 60 * 60 * 24 * 365;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLES_CLAIM = "roles";

    // Métodos públicos de la interfaz IJwtService
    @Override
    public String createAccessToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getUserId());
        extraClaims.put(ROLES_CLAIM, getRoleNames(user));
        return generateToken(user, extraClaims, ACCESS_EXPIRED);
    }

    @Override
    public String createRefreshToken(User user) {
        return generateToken(user, new HashMap<>(), REFRESH_EXPIRED);
    }

    @Override
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public boolean isSameUser(User user, String token) {
        return user.getEmail().equals(getEmailFromToken(token));
    }

    @Override
    public boolean tokenHasRole(HttpServletRequest request, RoleEnum roleEnum) {
        String token = getTokenFromRequest(request);
        @SuppressWarnings("unchecked") List<String> roles = getClaimFromToken(token, claims -> (List<String>) claims.get(ROLES_CLAIM));
        return roles != null && roles.contains(roleEnum.name());
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // Métodos privados de utilidad (que no están en la interfaz)
    private String generateToken(User user, Map<String, Object> extraClaims, long expiration) {
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getEmail()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private List<String> getRoleNames(User user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    @Override
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
}
