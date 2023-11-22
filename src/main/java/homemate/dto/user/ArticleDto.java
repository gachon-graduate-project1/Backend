package homemate.dto.user;
import homemate.domain.user.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 수정 불가
 */
public class ArticleDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ArticleRequestDto {


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

        private Long id;

        private Long userId; //게시글 작성한 사용자 id

        private String title; //제목

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)

        private List<CommentEntity> comments;

        private LocalDateTime createAt;



    }


}
