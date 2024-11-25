package hotel.hotel_spring.member.repository;

import hotel.hotel_spring.member.domain.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlackList, Long> {

    // 로그아웃 후 리스트에 담을 토큰 조회
    Optional<TokenBlackList> findByToken(String token);

    // blackList 삭제 후 autoincrement 초기화
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE tokenblacklist", nativeQuery = true)
    void resetTable();
}
