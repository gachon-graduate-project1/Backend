package homemate.domain.admin;
import homemate.constant.Role;
import homemate.constant.Status;
import homemate.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity extends TimeStamp {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String adminName;

    private String password;

    @Enumerated(EnumType.STRING)
    private final Role role = Role.ADMIN;

    @Enumerated(EnumType.STRING)
    private Status status;
}

