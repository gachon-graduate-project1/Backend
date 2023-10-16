package homemate.service.user;
import homemate.domain.admin.AdminEntity;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.ArticleDto;
import homemate.mapper.user.ArticleMapper;
import homemate.repository.user.ArticleRepository;
import homemate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;

    /**
     * 커뮤니티 게시글 수정 X, 삭제만 가능
     */
    @Transactional
    public ArticleDto.ArticleResponseDto createArticle(Long userId, ArticleDto.ArticleRequestDto articleRequestDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 User ID: " + userId));


        ArticleEntity savedArticle = articleRepository.save(articleMapper.toReqeustEntity(articleRequestDto, userEntity));
        ArticleDto.ArticleResponseDto responseDto = articleMapper.toResponseDto(savedArticle);

        return responseDto;
    }

    public ArticleDto.ArticleResponseDto getArticle(Long articleId) {

        return articleMapper.toResponseDto(articleRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 Article: " + articleId)));

    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
        log.info("삭제된 Article: {}",articleId);
    }


}
