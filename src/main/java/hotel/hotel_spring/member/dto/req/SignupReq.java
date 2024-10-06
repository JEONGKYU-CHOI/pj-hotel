package hotel.hotel_spring.member.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReq {

    // 회원가입 요청 DTO
    @NotBlank(message = "{error.signup.id}")
    private String id;

//    @NotBlank(message = "{error.signup.pw}")
//    @Min(12 message = "{error.signup.pw}")
    @Size(min = 12, message = "{error.signup.pw}")
    private String password;

    @Email(message = "{error.signup.email}")
    private String email;

    @NotBlank(message = "{error.signup.name}")
    private String name; // 한글, 영문 선택 입력 가능하게 개발 예정

    @NotBlank(message = "{error.signup.age}")
    private String age;

    @NotBlank(message = "{error.signup.sex}")
    private String sex;

//    @NotBlank(message = "{error.signup.tel}")
//    @Min(11, message = "{error.signup.tel}")
    @Size(min = 11, message = "{error.signup.tel}")
    private String telNum;

//    public SignupReq(String id, String password, String email, String name, int age, String sex, String telNum) {
//        this.id = id;
//        this.password = password;
//        this.email = email;
//        this.name = name;
//        this.age = age;
//        this.sex = sex;
//        this.telNum = telNum;
//    }
}
