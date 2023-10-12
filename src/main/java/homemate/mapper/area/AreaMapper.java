package homemate.mapper.area;


import homemate.domain.area.AreaEntity;
import homemate.dto.area.AreaDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AreaMapper {

    /**
     * Entity -> Dto
     */
    AreaDto.AreaResponseDto toResponseDto(AreaEntity areaEntity);

    /**
     * Dto -> Entity
     */
    @Mapping(target ="status", ignore = true )
    AreaEntity toResponseEntity(AreaDto.AreaResponseDto areaResponseDto);
}
