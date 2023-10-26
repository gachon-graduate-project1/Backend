package homemate.dto.user;
import homemate.constant.Role;
import homemate.constant.SocialType;
import jakarta.validation.constraints.NotNull;

import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRequestDto {

        /**
         * 소셜 서버에서 넘어오는 data 외 - nickName
         */
        @NotNull
        private String email;

        @NotNull
        private String nickName;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponseDto {

        private Long id;

        private String userName;

        private String nickName;

        private String password;

        private String email;

        private Role role;

        private SocialType socialType;

        private String socialId;

        private String refreshToken;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserPatchDto {

        /**
         * 소셜 로그인으로 인한 다른 항목 수정 불가
         */
        @NotNull
        private String nickName;

    }
}
