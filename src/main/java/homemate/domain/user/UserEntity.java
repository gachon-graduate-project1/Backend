package homemate.domain.user;
import homemate.constant.Role;
import homemate.constant.SocialType;
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
public class UserEntity extends TimeStamp {

    @Id
    @Column(name = "user_Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName; //소셜로그인 아이디

    private String nickName;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String refreshToken;

    // refreshToken 관련 연관관계 편의 메소드
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    //== 유저 필드 업데이트 ==//
    public void updateNickname(String updateNickname) {
        this.nickName = updateNickname;
    }

}
