package homemate.dto.user;
import lombok.*;

/**
 * 댓글 수정 불가
 */
public class CommentDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentPostDto {

        private ArticleDto articleDto; //댓글이 달린 게시글 dto

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentResponseDto {


        private ArticleDto.ArticleResponseDto article; //댓글이 달린 게시글 dto

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)

    }


}
