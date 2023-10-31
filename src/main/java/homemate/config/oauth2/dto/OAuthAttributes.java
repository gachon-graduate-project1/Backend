package homemate.config.oauth2.dto;

import homemate.config.jwt.util.PasswordUtil;
import homemate.config.oauth2.userinfo.KakaoOAuth2UserInfo;
import homemate.config.oauth2.userinfo.NaverOAuth2UserInfo;
import homemate.config.oauth2.userinfo.OAuth2UserInfo;
import homemate.constant.Role;
import homemate.constant.SocialType;
import homemate.constant.Status;
import homemate.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Slf4j
public class OAuthAttributes {
    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo){
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType, String usernameAttributeName, Map<String, Object> attributes){
        log.info("OAuthAttributes 진입");
        if(socialType == SocialType.KAKAO){
            return ofKakao(usernameAttributeName, attributes);
        }
        return ofNaver(usernameAttributeName, attributes);
    }

    public static OAuthAttributes ofKakao(String usernameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String usernameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public UserEntity toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo){
        return UserEntity.builder()
                .socialType(socialType)
                .socialId(oAuth2UserInfo.getId())
                .email(oAuth2UserInfo.getEmail())
                .password(PasswordUtil.generateRandomPassword())
                .role(Role.GUEST)
                .nickName("aaa")
                .userName("")
                .status(Status.ACTIVE)
                .build();
    }
}
