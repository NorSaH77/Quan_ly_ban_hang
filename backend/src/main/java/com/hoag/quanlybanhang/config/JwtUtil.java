package com.hoag.quanlybanhang.config;

import com.hoag.quanlybanhang.entity.NguoiDung;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

/**
 * Tiện ích JWT: tạo token, đọc thông tin từ token, xác thực token.
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;

    // Constructor: khởi tạo key từ chuỗi bí mật trong application.yml
    public JwtUtil(@Value("${jwt.secret}") String jwtSecret,
                   @Value("${jwt.expirationMs}") long jwtExpirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /** Tạo JWT token từ thông tin người dùng */
    public String generateToken(NguoiDung user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getVaiTro().name())
                .claim("name", user.getHoTen())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Lấy email từ token */
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /** Kiểm tra token có hợp lệ không */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Parse claims từ token */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Tạo UserDetails từ token (dùng cho Spring Security) */
    public UserDetails getUserDetails(String token) {
        Claims claims = parseClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
        return new User(email, "",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
    }
}
