package homemate.domain.area;
import homemate.constant.District;
import homemate.constant.Status;
import homemate.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

/**
 * 매물 지역 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaEntity extends TimeStamp {

    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private District district;


    @Enumerated(EnumType.STRING)
    private Status status;

}

