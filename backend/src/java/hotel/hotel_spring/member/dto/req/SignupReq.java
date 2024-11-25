package hotel.hotel_spring.member.dto.req;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReq {

    @Email(message = "{error.signup.email}")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,20}$", message = "{error.signup.pw}")
    private String password;

    @NotBlank(message = "{error.signup.name}")
    private String name; // 한글, 영문 선택 입력 가능하게 개발 예정

    @NotBlank(message = "{error.signup.age}")
    private String age;

    @NotBlank(message = "{error.signup.sex}")
    private String sex;

    @Size(min = 11, message = "{error.signup.tel}")
    private String telNum;
}
