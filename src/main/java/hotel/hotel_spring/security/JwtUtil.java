package hotel.hotel_spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret-key}")
    private String secretKey;

    // 전달된 UserDetails 객체의 username 정보를 기반으로 JWT를 생성합니다.
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())                          // JWT의 주제(subject)를 사용자의 username으로 설정합니다.
                .setIssuedAt(new Date())                                        // JWT 발급 시간을 현재 시각으로 설정합니다.
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // JWT 만료 시간을 발급 시간으로부터 하루 후로 설정합니다.
                .signWith(SignatureAlgorithm.HS256, secretKey)                  // 비밀 키 secret을 사용하여 HMAC SHA-256 알고리즘으로 서명을 추가합니다.
                .compact();                                                     // 최종적으로 JWT 토큰 문자열을 생성하여 반환합니다.
    }

    // JWT 토큰에서 사용자 이름 (username)을 추출합니다.
    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)                                      // JWT 토큰이 비밀 키 secret으로 서명되었음을 검증합니다.
                .parseClaimsJws(token)                                         // 토큰의 서명이 유효하면, 토큰의 페이로드(claims)를 가져옵니다.
                .getBody()
                .getSubject();                                                 // 페이로드에서 주제(subject)를 추출하여 반환합니다. 이 값은 생성 시에 username으로 설정되었습니다.
    }

    // JWT 토큰이 유효한지 검증합니다.
    // 토큰에서 username을 추출하고 UserDetails username과 일치 여부 및 토큰 만료 여부 체크 후 true, false 반환
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // tJWT 토큰이 만료되었는지 확인합니다.
    // 토큰 만료날짜와 현재 시간을 비교하여 true, false 반환
    public Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
    }

    // JWT 토큰의 만료 날짜를 추출합니다.
    // 토큰의 클레임을 가져와서 만료시간을 반환
    // * 토큰 클레임이란, 토큰 안에 사용자 정보 및 데이터 속성을 말함
    private Date extractExpiration(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
