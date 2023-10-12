package homemate.dto.user;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 게시글 수정 불가
 */
public class ArticleDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ArticlePostDto {


        @NotBlank
        private String title; //제목

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ArticleResponseDto {

        private UserDto.UserResponseDto user;


        private String title; //제목

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)

    }


}
