package homemate.mapper.user;
import homemate.domain.user.UserEntity;
import homemate.dto.user.UserDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    /**
     * Entity -> Dto
     */
    UserDto.UserResponseDto toResponseDto(UserEntity userEntity);

    /**
     * Dto -> Entity
     */
    @Mapping(target ="password", ignore = true )
    @Mapping(target ="status", ignore = true )
    UserEntity toResponseEntity(UserDto.UserResponseDto userResponseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore = true)
    @Mapping(target="createAt", ignore = true)
    @Mapping(target="modifiedAt", ignore = true)
    @Mapping(target="status", ignore = true)
    @Mapping(target="userName", ignore = true)
    @Mapping(target="password", ignore = true)
    @Mapping(target="email", ignore = true)
    @Mapping(target="socialType", ignore = true)
    @Mapping(target="socialId", ignore = true)
    @Mapping(target="refreshToken", ignore = true)
    void updateFromPatchDto(UserDto.UserPatchDto userPatchDto, @MappingTarget UserEntity userEntity);
}
