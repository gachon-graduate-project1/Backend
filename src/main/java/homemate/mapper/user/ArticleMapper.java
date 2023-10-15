package homemate.mapper.user;

import homemate.domain.user.ArticleEntity;
import homemate.dto.user.ArticleDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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

    @Mapping(target ="id", ignore = true )
    @Mapping(target ="status", ignore = true )
    @Mapping(source = "userId", target = "user.id") //게시글 작성자 매핑
    ArticleEntity toResponseEntity(ArticleDto.ArticleResponseDto articleResponseDto);


}

