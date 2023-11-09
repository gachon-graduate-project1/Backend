package homemate.controller.user;

import homemate.config.jwt.service.JwtService;
import homemate.dto.user.UserDto;
import homemate.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 시 추가 정보 저장 api
     */
//    @GetMapping("/sign-up")
//    public String signUpForm() {
//        return "user/sign-up"; // signup.html 또는 다른 뷰 페이지를 반환
//    }
    @GetMapping("/login/oauth2/code/user/sign-up")
    public ResponseEntity<?> joinUser(HttpServletRequest request, @RequestBody UserDto.UserRequestDto userRequestDto){
        log.info("회원가입 api 실행");
        try{
            // Header에서 Bearer부분을 제외한 jwt 추출
            String authorizationHeader = request.getHeader("Authorization");


            // 추출한 jwt에서 이메일값 추출
            Optional<String> email = jwtService.extractEmail(authorizationHeader.substring(7));

            // 이메일 값이 null이 아니면 회원가입 진행
            if(email.isPresent()){
                String value = email.get();
                userService.addJoinUserInfo(value, userRequestDto.getNickName());
                return ResponseEntity.ok().body("sign-up complete");
            } else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("empty email");
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sign-up error");
        }
    }


    /**
     * logout api -> 시큐리티에서 처리
     */



    /**
     *
     * update -> 닉네임만 설정 및 수정 가능 -> 온보딩
     */
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.UserPatchDto userPatchDto) {
        return ResponseEntity.ok().body(userService.updateUser(userId, userPatchDto));
    }

    /**
     * 사용자 탈퇴 기능
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Deleted UserId: " + userId);
    }




}
