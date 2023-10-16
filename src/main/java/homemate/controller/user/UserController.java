package homemate.controller.user;
import homemate.dto.user.UserDto;
import homemate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    //TODO: 소셜로그인 구현 후 join, login 작성
    /**
     * join 컨틀롤러
     */

    /**
     * login 컨트롤러
     */

    /**
     * logout 컨트롤러
     */

    @GetMapping("/get")
    public ResponseEntity<?> getUser (@RequestParam("userId") Long userId) {
        userService.getUser(userId);
        return ResponseEntity.ok().body("조회된 Id: " + userId);
    }

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
