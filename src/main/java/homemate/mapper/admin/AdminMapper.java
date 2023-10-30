package homemate.mapper.admin;


import homemate.domain.admin.AdminEntity;
import homemate.dto.admin.AdminDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AdminMapper {
    /**
     * Entity -> Dto
     */
    AdminDto.AdminResponseDto toResponseDto(AdminEntity adminEntity);

    /**
     * Dto -> Entity
     */
    @Mapping(target ="password", ignore = true )
    @Mapping(target ="status", ignore = true )
    AdminEntity toResponseEntity(AdminDto.AdminResponseDto adminResponseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore = true)
    @Mapping(target="createAt", ignore = true)
    @Mapping(target="modifiedAt", ignore = true)
    @Mapping(target="status", ignore = true)
    void updateFromPatchDto(AdminDto.AdminPatchDto adminPatchDto, @MappingTarget AdminEntity adminEntity);



}

