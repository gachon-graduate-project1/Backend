package homemate.service.admin;

import homemate.domain.admin.AdminEntity;
import homemate.dto.admin.AdminDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.admin.AdminMapper;
import homemate.repository.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
//   private final BCryptPasswordEncoder bCryptPasswordEncoder; TODO 시큐리티 적용 후 패스워드 수정
    /**
     * admin 회원가입 create x
     */

    public AdminDto.AdminResponseDto getAdmin(Long adminId) {
        // Entity 조회
        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));

        // Entity를 DTO로 변환 후 return
        return adminMapper.toResponseDto(adminEntity);
    }

    @Transactional
    public AdminDto.AdminResponseDto updateAdmin(Long adminId,AdminDto.AdminPatchDto adminPatchDto) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));


        //TODO 시큐리티 적용 후 패스워드 수정 (관리자 아이디 하나로 패스워드만 변경 가능)
       // if(adminPatchDto.getPassword()!=null) adminPatchDto.setPassword(bCryptPasswordEncoder.encode(adminPatchDto.getPassword()));

        // UserPatchDto에서 변경된 필드 UserEntity에 반영
        adminMapper.updateFromPatchDto(adminPatchDto,adminEntity);

        return adminMapper.toResponseDto(adminEntity);
    }

    @Transactional
    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
        log.info("삭제된 아이디: {}",adminId);
    }

}
