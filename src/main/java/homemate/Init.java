package homemate;
import homemate.constant.SocialType;
import homemate.constant.Status;
import homemate.domain.user.UserEntity;
import homemate.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init {

    private final UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void initUsers() {
        for (int i = 0; i < 5; i++) {
            UserEntity user = new UserEntity();
            user.setUserName("user" + i);
            user.setNickName("userNickname" + i);
            user.setPassword("userPs" + i);
            user.setEmail("user" + i + "@example.com"); // 수정된 이메일 주소
            user.setStatus(Status.ACTIVE); // Status 열거형 값 설정
            user.setSocialType(SocialType.KAKAO); // SocialType 열거형 값 설정
            user.setSocialId("userSocialId" + i);
            user.setRefreshToken("userRefreshToken" + i);
            userRepository.save(user);
            log.info("User ID: " + user.getId()); // 로깅 메시지 수정
        }
    }
}