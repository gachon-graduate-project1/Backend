package homemate.service.user;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.ArticleDto;
import homemate.dto.user.CommentDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.user.CommentMapper;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    /**
     * 댓글 수정 X, 삭제만 가능
     */
    @Transactional
    public CommentDto.CommentResponseDto createComment(Long userId, CommentDto.CommentRequestDto commentRequestDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));


        CommentEntity savedComment = commentRepository.save(commentMapper.toRequestEntity(commentRequestDto, userEntity));
        CommentDto.CommentResponseDto responseDto = commentMapper.toResponseDto(savedComment);

        return responseDto;
    }



    public CommentDto.CommentResponseDto getComment(Long commentId) {

        return commentMapper.toResponseDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_IS_NOT_EXIST)));

    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        log.info("삭제된 Comment: {}",commentId);
    }


    /**
     * 댓글 신고
     */
    @Transactional
    public CommentDto.CommentResponseDto complainComment(Long commentId) {

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() ->  new BusinessLogicException(ExceptionCode.COMMENT_IS_NOT_EXIST));

        //신고 + 1
        int complain = commentEntity.getComplain();
        complain++;

        //10회 이상일 경우 게시글 삭제
        if(complain >= 10){

            //댓글 삭제
            deleteComment(commentId);
            return null;
        }
        else {
            commentEntity.setComplain(complain);
            log.info("Complain count: ", complain);
            return commentMapper.toResponseDto(commentEntity);

        }
    }

}
