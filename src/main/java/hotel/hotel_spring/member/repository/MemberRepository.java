package hotel.hotel_spring.member.repository;

import hotel.hotel_spring.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // 회원가입 및 JWT 검증 필요
    // ID 중복체크
    Optional<Member> findById(String id);

}
