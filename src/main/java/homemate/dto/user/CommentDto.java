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
    public static class CommentRequestDto {

        private Long articleId; //댓글이 달린 게시글 dto

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CommentResponseDto {

        private Long id;

        private Long userId; //댓글 작성한 사용자 id

        private Long articleId; //댓글이 달린 게시글 id

        private String content; //내용

        private Integer complain; // 게시글 신고 횟수 (최대 10회)


    }




}
