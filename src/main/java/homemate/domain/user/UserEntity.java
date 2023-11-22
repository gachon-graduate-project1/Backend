package homemate.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import homemate.constant.Role;
import homemate.constant.SocialType;
import homemate.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends TimeStamp {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName; //소셜로그인 아이디

    private String nickName;

    private String password;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ArticleEntity> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CommentEntity> comments = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;


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
