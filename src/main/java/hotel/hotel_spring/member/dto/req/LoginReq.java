package hotel.hotel_spring.member.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReq {

    private String email;
    private String password;
}
