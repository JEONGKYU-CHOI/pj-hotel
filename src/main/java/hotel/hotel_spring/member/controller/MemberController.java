package hotel.hotel_spring.member.controller;

import hotel.hotel_spring.member.dto.req.LoginReq;
import hotel.hotel_spring.member.dto.req.SignupReq;
import hotel.hotel_spring.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor // 생성자 자동 생성
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    private MessageSource messageSource;

    @Operation(summary = "회원가입 요청")
    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@Valid @RequestBody SignupReq signupReq){

        // ID 중복체크
        if (memberService.memberIdCheck(signupReq.getEmail())){
            String errorMsg = messageSource.getMessage("error.signup.already.exists", null, Locale.getDefault());

            return new ResponseEntity<>(errorMsg, HttpStatus.CONFLICT);
        }

        // DB 저장
        memberService.saveMember(signupReq);
        String errorMsg = messageSource.getMessage("success.signup", null, Locale.getDefault());
        return ResponseEntity.ok(errorMsg);
    }

    // 유효성 검사 실패 시 처리하는 핸들러
    // MethodArgumentNotValidException 예외 핸들러는 검증 실패 시 발생하는 에러들을 처리하여 필드명과 에러 메시지를 반환한다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // 필드명
            String errorMessage = error.getDefaultMessage(); // 필드명에 맞는 에러 메시지
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginReq loginReq){

        String token = memberService.login(loginReq);

        return ResponseEntity.ok(token);
    }
}

