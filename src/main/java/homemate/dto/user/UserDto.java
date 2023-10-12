package homemate.dto.user;
import homemate.constant.SocialType;
import jakarta.validation.constraints.NotNull;

import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserPostDto {

        /**
         * 클라이언트에서 넘어오는 data
         */

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
