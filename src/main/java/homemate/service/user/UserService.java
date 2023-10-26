package homemate.service.user;

import homemate.domain.user.UserEntity;
import homemate.dto.user.UserDto;
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
                .orElseThrow(() -> new NoSuchElementException("회원가입 안 된 email"));

        // 회원 추가 정보(닉네임) 저장 및 권한 변경
        userEntity.authorizeUser();
        userEntity.setNickName(nickName);
        userRepository.save(userEntity);

    }

    public UserDto.UserResponseDto getUser(Long userId) {
        // Entity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 User ID: " + userId));

        // Entity를 DTO로 변환 후 return
        return userMapper.toResponseDto(userEntity);
    }


    @Transactional
    public UserDto.UserResponseDto updateUser(Long userId,UserDto.UserPatchDto userPatchDto) {

        // Entity 조회
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 User ID: " + userId));

        //회원정보 수정은 닉네임만 가능
        if(userPatchDto.getNickName()!=null) userPatchDto.setNickName(userPatchDto.getNickName());

        // UserPatchDto에서 변경된 필드 UserEntity에 반영
        userMapper.updateFromPatchDto(userPatchDto,userEntity);


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
    
    
    
}
