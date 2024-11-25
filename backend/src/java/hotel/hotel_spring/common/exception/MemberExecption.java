package hotel.hotel_spring.common.exception;

// 회원가입 및 로그인 관련 에러 메시지 관리
public class MemberExecption extends RuntimeException{
    public MemberExecption(String msg){
        super(msg);
    }
}
