package homemate.controller.user;

import homemate.dto.user.ArticleDto;
import homemate.dto.user.CommentDto;
import homemate.service.user.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

    /**
     * 댓글 수정 X
     */

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto.CommentResponseDto> createComment(@RequestParam("userId") Long userId, @RequestBody CommentDto.CommentRequestDto commentRequestDto) {
        CommentDto.CommentResponseDto responseDto = commentService.createComment(userId, commentRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getComment(@RequestParam("commentId") Long commentId) {
        return ResponseEntity.ok().body(commentService.getComment(commentId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body("삭제된 Comment Id : " + commentId);
    }
}
