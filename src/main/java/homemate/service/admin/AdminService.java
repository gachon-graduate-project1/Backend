package homemate.service.admin;

import homemate.domain.admin.AdminEntity;
import homemate.domain.building.BuildingEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.admin.AdminDto;
import homemate.dto.building.BuildingDto;
import homemate.dto.user.UserDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.admin.AdminMapper;
import homemate.mapper.user.UserMapper;
import homemate.repository.admin.AdminRepository;
import homemate.repository.building.BuildingRepository;
import homemate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminMapper adminMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final BuildingRepository buildingRepository;


    /**
     * 로그인 기능
     */

    @Transactional
    public AdminDto.AdminResponseDto login(AdminDto.AdminRequestDto adminRequestDto) {

        AdminEntity adminEntity = adminRepository.findByAdminName(adminRequestDto.getAdminName())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.UNAUTHRORIZED_ADMIN));


        log.info("adminName: {}", adminEntity.getAdminName());

        // 비밀번호 비교
        if (!adminRequestDto.getPassword().equals(adminEntity.getPassword())) {

            throw new BusinessLogicException(ExceptionCode.UNAUTHRORIZED_ADMIN);
        }

        //사용자 정보 반환
        AdminDto.AdminResponseDto responseDto = adminMapper.toResponseDto(adminEntity);

        return responseDto;
    }




    public AdminDto.AdminResponseDto getAdmin(Long adminId) {
        // Entity 조회
        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));

        // Entity를 DTO로 변환 후 return
        return adminMapper.toResponseDto(adminEntity);
    }

    /**
     * 비밀번호만 수정
     */
    @Transactional
    public AdminDto.AdminResponseDto updateAdmin(Long adminId,AdminDto.AdminPatchDto adminPatchDto) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));

        if(adminPatchDto.getPassword()!=null) adminPatchDto.setPassword(passwordEncoder.encode(adminPatchDto.getPassword()));

        // UserPatchDto에서 변경된 필드 UserEntity에 반영
        adminMapper.updateFromPatchDto(adminPatchDto,adminEntity);

        return adminMapper.toResponseDto(adminEntity);
    }

//    @Transactional
//    public void deleteAdmin(Long adminId) {
//        adminRepository.deleteById(adminId);
//        log.info("삭제된 아이디: {}",adminId);
//    }

    public Page<UserDto.UserResponseDto> getAllUser(int page, int size){
        // 페이지 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> userEntities = userRepository.getAllUser(pageable);
        // 페이지를 Dto로 변환

        log.info("dto 변환 시작");
        Page<UserDto.UserResponseDto> userList = userEntities.map(m ->
                UserDto.UserResponseDto.builder()
                        .id(m.getId())
                        .userName(m.getUserName())
                        .email(m.getEmail())
                        .nickName(m.getNickName())
                        .password(m.getPassword())
                        .role(m.getRole())
                        .socialId(m.getSocialId())
                        .socialType(m.getSocialType())
                        .refreshToken(m.getRefreshToken())
                        .build());


        return userList;
    }

    public Page<BuildingDto.BuildingResponseDto> getAllBuilding(int page, int size){
        // 페이지 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<BuildingEntity> buildingEntities = buildingRepository.getAllBuilding(pageable);
        // 페이지를 Dto로 변환

        log.info("dto 변환 시작");
        Page<BuildingDto.BuildingResponseDto> buildingList = buildingEntities.map(m ->
                BuildingDto.BuildingResponseDto.builder()
                        .id(m.getId())
                        .address(m.getAddress())
                        .content(m.getContent())
                        .floor(m.getFloor())
                        .warantPrice(m.getWarantPrice())
                        .dealPrice(m.getDealPrice())
                        .rentPrice(m.getRentPrice())
                        .moveInDate(m.getMoveInDate())
                        .checkDuplex(m.getCheckDuplex())
                        .direction(m.getDirection())
                        .numberOfParking(m.getNumberOfParking())
                        .realterName(m.getRealterName())
                        .realterNumber(m.getRealterNumber())
                        .buildingField(m.getBuildingField())
                        .buildingName(m.getBuildingName())
                        .numberOfRoom(m.getNumberOfRoom())
                        .images(m.getImages())
                        .transactioonType(m.getTransactioonType())
                        .build());


        return buildingList;
    }

    public UserDto.UserResponseDto getDetailUser(Long userId){
        // UserEntity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원: " + userId));

        // Entity -> Dto 변환
        return userMapper.toResponseDto(userEntity);

    }
    @Transactional
    public UserDto.UserResponseDto updateUser(Long userId, UserDto.AdminPatchUserDto adminPatchUserDto){
        // UserEntity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원: " + userId));

        userEntity.setPassword(adminPatchUserDto.getPassword());
        userEntity.setNickName(adminPatchUserDto.getNickName());

        return userMapper.toResponseDto(userEntity);


    }

    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
        log.info("삭제된 아이디: {}", userId);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
