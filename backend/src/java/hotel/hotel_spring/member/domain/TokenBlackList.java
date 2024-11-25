package hotel.hotel_spring.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tokenblacklist")
@Builder
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    private String token;
    private Date expiryDate;

    @Builder
    public TokenBlackList(Long id, String token, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
