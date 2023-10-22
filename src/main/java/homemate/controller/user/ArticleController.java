package homemate.controller.user;
import homemate.dto.user.ArticleDto;
import homemate.service.user.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("article")
public class ArticleController {

    /**
     * 게시글 수정 X
     */

    private final ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<ArticleDto.ArticleResponseDto> createArticle(@RequestParam("userId") Long userId, @RequestBody ArticleDto.ArticleRequestDto articleRequestDto) {
        ArticleDto.ArticleResponseDto responseDto = articleService.createArticle(userId, articleRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getArticle(@RequestParam("articleId") Long articleId) {
        ArticleDto.ArticleResponseDto article = articleService.getArticle(articleId);
        return ResponseEntity.ok().body(article);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteArticle(@RequestParam Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok().body("삭제된 Article Id : " + articleId);
    }

    /**
     * 게시글 검색
     */

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDto.ArticleResponseDto>> searchArticle(@RequestParam String keyword) {
        List<ArticleDto.ArticleResponseDto> articles = articleService.searchArticle(keyword);
        return ResponseEntity.ok().body(articles);

    }

    /**
     * 게시글 신고
     */

    @PatchMapping("/addComplain")
    public ResponseEntity<?> addComplain(@RequestParam("articleId") Long articleId) {
        return ResponseEntity.ok().body(articleService.complainArticle(articleId));
    }
}
