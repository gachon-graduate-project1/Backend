package homemate.domain;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //자동 시간 매핑
public abstract class TimeStamp {


    @CreatedDate
    private LocalDateTime createAt;

//    @LastModifiedDate
//    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createAt = now;
//        modifiedAt = now;
    }


}
