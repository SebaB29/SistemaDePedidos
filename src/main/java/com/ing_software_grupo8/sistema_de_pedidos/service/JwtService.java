package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private final long ACCESS_EXPIRED = 1000 * 60 * 24;
    private final long REFRESH_EXPIRED = 1000 * 60 * 60 * 24 * 365;

    @Override
    public String createAccessToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getUserId());
        extraClaims.put("roles", user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return getToken(user, extraClaims, ACCESS_EXPIRED);
    }

    @Override
    public String createRefreshToken(User user) {
        return getToken(user, new HashMap<>(), REFRESH_EXPIRED);
    }

    private String getToken(User user, Map<String, Object> extraClaims, long expired) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String email = getEmailFromToken(token);
        return (email.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
            return authHeader.substring(7);
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean tokenHasRoleAdmin(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        List<String> roles = getClaim(token, claims -> claims.get("roles", List.class));
        return roles != null && roles.contains(RoleEnum.ADMIN.name());
    }

    @SuppressWarnings("unchecked")
    public boolean tokenHasRoleUser(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        List<String> roles = getClaim(token, claims -> claims.get("roles", List.class));
        return roles != null && roles.contains(RoleEnum.USER.name());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        try {
            return getExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Override
    public boolean isSameUser(User user, String token) {
        return user.getEmail().equals(getEmailFromToken(token));
    }

}