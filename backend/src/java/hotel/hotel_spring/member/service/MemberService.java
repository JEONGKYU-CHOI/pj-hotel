package hotel.hotel_spring.member.service;

import hotel.hotel_spring.member.domain.Member;
import hotel.hotel_spring.member.domain.TokenBlackList;
import hotel.hotel_spring.member.dto.req.LoginReq;
import hotel.hotel_spring.member.dto.req.SignupReq;
import hotel.hotel_spring.member.repository.MemberRepository;
import hotel.hotel_spring.member.repository.TokenBlacklistRepository;
import hotel.hotel_spring.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ID 중복 체크
    public boolean memberIdCheck(String email){
        return memberRepository.findByEmail(email).isPresent();
    }

    // 회원가입
    public Member saveMember(SignupReq signupReq){

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(signupReq.getPassword());

        // ROLE 설정 [ 기본 user 설정 // 관리자 직접 admin 작업 예정]
        // 회사 이메일로 회원가입시 자동 Admin 권한 부여
        String defaultRole = signupReq.getEmail().endsWith("@JKhotel.com") ? "ADMIN" : "USER";

        // 회원 등급 설정 [ 기본 G / M ] 2단계 일반, 회원
        String defaultGrade = "G";

        // DB 저장
        Member member = new Member().builder()
                .password(encodePassword)
                .email(signupReq.getEmail())
                .name(signupReq.getName())
                .age(signupReq.getAge())
                .sex(signupReq.getSex())
                .telNum(signupReq.getTelNum())
                .grade(defaultGrade)
                .role(defaultRole)
                .build();
        return memberRepository.save(member);
    }

    // 로그인
    public String login(LoginReq loginReq){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

    }

    // 로그아웃
    public void logout(String token){

        // 토큰 유효성 검증
        if (!jwtUtil.isTokenExpired(token)){
            
            /* 토큰 유효한 경우
              1. jwtUtil에 만들어진 생성 로직을 활용한 토큰 / 만료시간 추출 
              2. 추출한 데이터 db저장
            */
            TokenBlackList tokenBlackList = new TokenBlackList();
            tokenBlackList.setToken(token);
            tokenBlackList.setExpiryDate(jwtUtil.extractExpiration(token));
            tokenBlacklistRepository.save(tokenBlackList);
        }else {
            //토큰 만료된 경우 -> 만료된 메시지 출력
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "만료된 유효하지 않은 토큰입니다.");


        }

    }

}
