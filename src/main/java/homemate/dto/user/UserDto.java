package homemate.dto.user;

import homemate.constant.Role;
import homemate.constant.SocialType;
import homemate.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
