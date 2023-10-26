package homemate.mapper.user;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.CommentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CommentMapper {

    /**
     * Entity -> Dto
     */
    @Mapping(source = "user.id", target = "userId") //게시글 작성자 매핑
    @Mapping(source = "article.id", target = "articleId") //게시글 작성자 매핑
    CommentDto.CommentResponseDto toResponseDto(CommentEntity commentEntity);

    /**
     * Dto -> Entity
     */

    @Mapping(target ="status", ignore = true )
    @Mapping(source = "userId", target = "user.id") //게시글 작성자 매핑
    @Mapping(source = "articleId", target = "article.id") //게시글 작성자 매핑
    CommentEntity toResponseEntity(CommentDto.CommentResponseDto commentResponseDto);

    @Mapping(target = "user", ignore = true) //게시글 작성자 매핑
    @Mapping(target = "article", ignore = true) //게시글 작성자 매핑
    CommentEntity toRequestEntity(CommentDto.CommentRequestDto commentRequestDto, UserEntity userEntity);

}
