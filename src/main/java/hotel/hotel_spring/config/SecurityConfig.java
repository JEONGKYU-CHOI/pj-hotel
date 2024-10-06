package hotel.hotel_spring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // WEB 보안 설정에 사용 됨
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }


//    리소스(URL) 접근 권한 설정
//    인증 전체 흐름에 필요한 Login, Logout 페이지 인증완료 후 페이지 인증 실패시 이동페이지 설정
//    인증 로직을 커스텀하기위한 필터 설정
//    기타 csrf, 강제 https 호출 등등 거의 모든 스프링시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 변경 전
//        http.csrf().disable();

        // 변경 후
        http.csrf(csrf -> csrf.disable());

        // 추후 JWT 추가 예정

//                .authorizeRequests()
//                .requestMatchers("/api/signup").permitAll() // 회원가입 API 인증 없이 접근 가능
//                .requestMatchers("/api/**").authenticated() // 그 외 나머지 API 인증 필요
//                .and()
//            .formLogin();

        return http.build();
    }
}
