package homemate.controller.user;

import homemate.dto.user.UserDto;
import homemate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService userService;

    //TODO: 소셜로그인 구현 후 join, login 작성
    /**
     * 회원가입 시 추가 정보 저장 api
     */
    @GetMapping("/sign-up")
    public String signUpForm() {
        return "user/sign-up"; // signup.html 또는 다른 뷰 페이지를 반환
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> joinUser(@RequestBody UserDto.UserRequestDto userRequestDto){
        log.info("회원가입 api 실행");
        try{
            userService.addJoinUserInfo(userRequestDto.getEmail(), userRequestDto.getNickName());
            return ResponseEntity.ok("회원가입 완료");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 오류 발생");
        }
    }


    /**
     * logout api -> 시큐리티에서 처리
     */



    @GetMapping("/getUser")
    public ResponseEntity<?> getUser (@RequestParam("userId") Long userId) {
        UserDto.UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }

    /**
     *
     * update -> 닉네임만 수정 가능 -> 온보딩 
     */
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.UserPatchDto userPatchDto) {
        return ResponseEntity.ok().body(userService.updateUser(userId, userPatchDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("삭제된 UserId: " + userId);
    }

    /**
     * 전체 회원 조회
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto.UserResponseDto>> getAllUser() {
        List<UserDto.UserResponseDto> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }
}
