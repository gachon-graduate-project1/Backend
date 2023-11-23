package homemate.mapper.user;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.ArticleDto;
import homemate.dto.user.CommentDto;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ArticleMapper {
    /**
     * Entity -> Dto
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nickName", target = "nickName")
    @Mapping(source = "comments", target = "comments")
    ArticleDto.ArticleResponseDto toResponseDto(ArticleEntity articleEntity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nickName", target = "nickName")
    @Mapping(source = "article.id", target = "articleId")
    CommentDto.CommentResponseDto toCommentResponseDto(CommentEntity commentEntity);

    default List<CommentDto.CommentResponseDto> mapCommentEntitiesToDto(List<CommentEntity> comments) {
        return comments.stream()
                .map(this::toCommentResponseDto)
                .collect(Collectors.toList());
    }





    /**
     * Dto -> Entity
     */

//    @Mapping(source = "userId", target = "user.id") //게시글 작성자 매핑
//    ArticleEntity toResponseEntity(ArticleDto.ArticleResponseDto articleResponseDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userEntity")
    ArticleEntity toReqeustEntity(ArticleDto.ArticleRequestDto articleRequestDto, UserEntity userEntity);






}

