package homemate.service.user;

import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.ArticleDto;
import homemate.dto.user.CommentDto;
import homemate.mapper.user.CommentMapper;
import homemate.repository.user.CommentRepository;
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
public class CommentService {

    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserRepository userRepository;

    /**
     * 댓글 수정 X, 삭제만 가능
     */
    @Transactional
    public CommentDto.CommentResponseDto createComment(Long userId, CommentDto.CommentRequestDto commentRequestDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 User ID: " + userId));


        CommentEntity savedComment = commentRepository.save(commentMapper.toRequestEntity(commentRequestDto, userEntity));
        CommentDto.CommentResponseDto responseDto = commentMapper.toResponseDto(savedComment);

        return responseDto;
    }

    public CommentDto.CommentResponseDto getComment(Long commentId) {

        return commentMapper.toResponseDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 Comment: " + commentId)));

    }

    @Transactional
    public void deleteArticle(Long commentId) {
        commentRepository.deleteById(commentId);
        log.info("삭제된 Comment: {}",commentId);
    }
}
