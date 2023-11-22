package homemate.service.user;
import homemate.domain.building.BuildingEntity;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.building.BuildingDto;
import homemate.dto.user.ArticleDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.user.ArticleMapper;
import homemate.repository.user.ArticleRepository;
import homemate.repository.user.CommentRepository;
import homemate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 커뮤니티 게시글 수정 X, 삭제만 가능
     */
    @Transactional
    public ArticleDto.ArticleResponseDto createArticle(Long userId, ArticleDto.ArticleRequestDto articleRequestDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));


        ArticleEntity savedArticle = articleRepository.save(articleMapper.toReqeustEntity(articleRequestDto, userEntity));

        ArticleDto.ArticleResponseDto responseDto = articleMapper.toResponseDto(savedArticle);
        responseDto.setUserId(userId);
        responseDto.setComplain(0);

        return responseDto;
    }




    public ArticleDto.ArticleResponseDto getArticle(Long articleId) {

        ArticleEntity articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_EXIST));

        return articleMapper.toResponseDto(articleEntity);

    }

    @Transactional
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
        log.info("삭제된 Article: {}",articleId);
    }

    @Transactional
    public List<ArticleDto.ArticleResponseDto> searchArticle(String keyword) {

        List<ArticleEntity> articleEntities = articleRepository.findKeyword(keyword);
        List<ArticleDto.ArticleResponseDto> articleResponseDtos = new ArrayList<>();

        if (articleEntities.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.KEYWORD_IS_NOT_EXIST);
        }


        for (ArticleEntity ArticleEntity :articleEntities){
            ArticleDto.ArticleResponseDto articleResponseDto = articleMapper.toResponseDto(ArticleEntity);
            articleResponseDtos.add(articleResponseDto);
        }

        return articleResponseDtos;
    }

    /**
     * 게시글 신고
     */
    @Transactional
    public ArticleDto.ArticleResponseDto complainArticle(Long articleId) {

        ArticleEntity articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_EXIST));

        //신고 + 1
        int complain = articleEntity.getComplain();
        complain++;

        //10회 이상일 경우 게시글 삭제
        if(complain >= 10){
            //영속성 문제로 게시글에 달린 댓글 먼저 삭제
            commentRepository.deleteByArticle(articleEntity);
            //게시글 삭제
            deleteArticle(articleId);
            return null;
        }
        else {
            articleEntity.setComplain(complain);
            log.info("Complain count: ", complain);
            return articleMapper.toResponseDto(articleEntity);

        }
    }

    /**
     * 전체 게시글 조회
     */
    @Transactional
    public List<ArticleDto.ArticleResponseDto> getAllArticle(){

        List<ArticleEntity> articleEntities = articleRepository.getAllArticle();
        List<ArticleDto.ArticleResponseDto> articleResponseDtos = new ArrayList<>();

        for (ArticleEntity articleEntity : articleEntities) {
            ArticleDto.ArticleResponseDto articleResponseDto = articleMapper.toResponseDto(articleEntity);
            articleResponseDtos.add(articleResponseDto);
        }

        return articleResponseDtos;
    }



}
