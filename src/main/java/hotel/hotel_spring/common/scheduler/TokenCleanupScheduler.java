package hotel.hotel_spring.common.scheduler;

import hotel.hotel_spring.member.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
public class TokenCleanupScheduler {


    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    /*
    *  사용방법
    *  초 분 시 일 월 요일 년도(생략가능)
    * */
    @Scheduled(cron = "0 20 21 * * *", zone = "Asia/Seoul")
    public void deleteTokenBlackList(){
        // 전체 삭제
        tokenBlacklistRepository.deleteAll();

        // autoIncrement 초기화
        tokenBlacklistRepository.resetTable();
    }
}
