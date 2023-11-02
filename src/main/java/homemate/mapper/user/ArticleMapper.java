package homemate.mapper.user;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.user.ArticleDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ArticleMapper {
    /**
     * Entity -> Dto
     */
    @Mapping(source = "user.id", target = "userId") //게시글 작성자 매핑
    ArticleDto.ArticleResponseDto toResponseDto(ArticleEntity articleEntity);





    /**
     * Dto -> Entity
     */

    @Mapping(target ="status", ignore = true )
    @Mapping(source = "userId", target = "user.id") //게시글 작성자 매핑
    ArticleEntity toResponseEntity(ArticleDto.ArticleResponseDto articleResponseDto);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "complain", ignore = true)
    ArticleEntity toReqeustEntity(ArticleDto.ArticleRequestDto articleRequestDto, UserEntity userEntity);






}

