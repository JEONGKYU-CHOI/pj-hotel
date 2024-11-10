package hotel.hotel_spring.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // WEB 보안 설정에 사용 됨 // 스프링 시큐리티 필터 작동
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


//    리소스(URL) 접근 권한 설정
//    인증 전체 흐름에 필요한 Login, Logout 페이지 인증완료 후 페이지 인증 실패시 이동페이지 설정
//    인증 로직을 커스텀하기위한 필터 설정
//    기타 csrf, 강제 https 호출 등등 거의 모든 스프링시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* 변경 전
        *  http.csrf(csrf -> csrf.disable());
        */
//        http.csrf(csrf -> csrf.disable());
        // 변경 후

        http
            .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자 전용
                        .requestMatchers("/api/user/**").hasRole("USER")   // 사용자 전용
//                        .requestMatchers("/api/member/**", "/api/login/**").permitAll()
                        .requestMatchers( "swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**", "/api/member/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        // 추후 JWT 추가 예정

//                .authorizeRequests()
//                .requestMatchers("/api/signup", /swagger-ui/index.htm).permitAll() // 회원가입 API 인증 없이 접근 가능
//                .requestMatchers("/api/member/**").a
//                .requestMatchers("/api/**").authenticated() // 그 외 나머지 API 인증 필요
//                .and()
//            .formLogin();

        return http.build();
    }
}
