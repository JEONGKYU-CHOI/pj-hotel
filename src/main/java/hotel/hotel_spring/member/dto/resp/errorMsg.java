package hotel.hotel_spring.member.dto.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class errorMsg {

    private String erCode;
    private String erMsg;

    public errorMsg(String erCode, String erMsg) {
        this.erCode = erCode;
        this.erMsg = erMsg;
    }
}
