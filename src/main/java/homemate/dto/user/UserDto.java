package homemate.dto.user;

import homemate.constant.Role;
import homemate.constant.SocialType;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


public class UserDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRequestDto {



        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
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

        private List<ArticleEntity> articles;

        private List<CommentEntity> comments;

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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AdminPatchUserDto{

        private Long userId;


        private String nickName;
    }
}
