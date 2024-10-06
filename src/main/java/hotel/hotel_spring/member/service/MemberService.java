package hotel.hotel_spring.member.service;

import hotel.hotel_spring.member.domain.Member;
import hotel.hotel_spring.member.dto.req.SignupReq;
import hotel.hotel_spring.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    final MemberRepository memberRepository;
    final PasswordEncoder passwordEncoder;

    // ID 중복 체크
    public boolean memberIdCheck(String id){
        return memberRepository.findById(id).isPresent();
    }

    // 회원가입
    public Member saveMember(SignupReq req){

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(req.getPassword());

        // ROLE 설정 [ 기본 user 설정 // 관리자 직접 admin 작업 예정]
        // 회사 이메일로 회원가입시 자동 Admin 권한 부여
        String defaultRole = req.getEmail().endsWith("@hotel.com") ? "ROLE_ADMIN" : "ROLE_USER";

        // 회원 등급 설정 [ 기본 G / M ] 2단계 일반, 회원
        String defaultGrade = "G";

        // DB 저장
        Member member = new Member().builder()
                .id(req.getId())
                .password(encodePassword)
                .email(req.getEmail())
                .name(req.getName())
                .age(req.getAge())
                .sex(req.getSex())
                .telNum(req.getTelNum())
                .grade(defaultGrade)
                .role(defaultRole)
                .build();
        return memberRepository.save(member);

    }

}
