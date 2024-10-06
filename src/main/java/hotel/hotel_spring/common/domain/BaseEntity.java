package hotel.hotel_spring.common.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime firstDt;

    private LocalDateTime lastDt;

    @PrePersist // 등록
    protected void create() {
        this.firstDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void update() {
        this.lastDt = LocalDateTime.now();
    }

}
