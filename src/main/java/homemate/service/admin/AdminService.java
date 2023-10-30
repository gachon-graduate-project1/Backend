package homemate.service.admin;

import homemate.domain.admin.AdminEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.admin.AdminDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.dto.user.UserDto;
import homemate.mapper.admin.AdminMapper;
import homemate.mapper.user.UserMapper;
import homemate.repository.admin.AdminRepository;
import homemate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    /**
     * admin 회원가입 create x (한 개의 계정으로 비밀번호만 변경 가능)
     */



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

    @Transactional
    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
        log.info("삭제된 아이디: {}",adminId);
    }

    public Page<UserDto.UserResponseDto> getAllUser(int page, int size){
        // 페이지 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        // 페이지를 Dto로 변환
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

}
