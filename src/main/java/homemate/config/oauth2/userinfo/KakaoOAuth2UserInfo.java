package homemate.config.oauth2.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes){
        super(attributes);
    }
    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
//        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
//
//        if(account == null || profile == null) {
//            return null;
//        }
//        return (String) account.get("email");
        //return (String) attributes.get("email");
        return (String) ((Map<String, Object>) attributes.get("kakao_account")).get("email");
    }

//    @Override
//    public String getUsername() {
//        Map<String, Object> name = (Map<String, Object>) attributes.get("name");
//        return (String) name.get("name");
//    }
}
