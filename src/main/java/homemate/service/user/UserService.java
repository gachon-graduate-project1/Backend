package homemate.service.user;

import homemate.domain.user.UserEntity;
import homemate.dto.user.UserDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.user.UserMapper;
import homemate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //회원가입은 소셜로그인 통해 createUser X
    @Transactional
    public void addJoinUserInfo(String email, String nickName){

        log.info("회원가입 service 실행");
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        // 닉네임 중복 체크
        if(userRepository.existsByNickName(nickName)){
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME);
        } else {
            // 회원 추가 정보(닉네임) 저장 및 권한 변경
            userEntity.authorizeUser();
            userEntity.setNickName(nickName);
            userRepository.save(userEntity);
        }


    }

    public UserDto.UserResponseDto getUser(Long userId) {
        // Entity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // Entity를 DTO로 변환 후 return
        return userMapper.toResponseDto(userEntity);
    }


    /**
     * 회원정보 닉네임만 수정가능
     */
    @Transactional
    public UserDto.UserResponseDto updateUser(Long userId,UserDto.UserPatchDto userPatchDto) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() ->new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        String newNickName = userPatchDto.getNickName();

        // 닉네임 중복성 확인.
        if (newNickName != null) {

            boolean isNickNameUnique = userRepository.existsByNickName(newNickName);

            if (!isNickNameUnique) {
                throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME);
            }

            userEntity.setNickName(newNickName);
        }
        return userMapper.toResponseDto(userEntity);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        log.info("삭제된 User 아이디: {}",userId);
    }


    /**
     * 전체 회원 조회
     */
    @Transactional
    public List<UserDto.UserResponseDto> getAllUser(){

        List<UserEntity> userEntities = userRepository.getAllUser();
        List<UserDto.UserResponseDto> userResponseDtos = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            UserDto.UserResponseDto userResponseDto = userMapper.toResponseDto(userEntity);
            userResponseDtos.add(userResponseDto);
        }

        return userResponseDtos;
    }

//    /**
//     * 닉네임 중복 에러
//     */
//    public class DuplicateNicknameException extends RuntimeException {
//        public DuplicateNicknameException(String message) {
//            super(message);
//        }
//    }





}
