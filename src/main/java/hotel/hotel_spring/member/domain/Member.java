package hotel.hotel_spring.member.domain;

import hotel.hotel_spring.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;
    private String name; // 한글, 영문 선택 입력 가능하게 개발 예정
    private String age;
    private String sex;
    private String telNum;
    private String grade; // 회원 등급 [G, M] 2단계 일반, 회원
    private String role; // 권한 설정

    @Builder
    public Member(Long memberId, String email, String password, String name, String age, String sex, String telNum, String grade, String role) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.telNum = telNum;
        this.grade = grade;
        this.role = role;
    }
}
