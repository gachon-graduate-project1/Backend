package homemate.controller.admin;

import homemate.domain.admin.AdminEntity;
import homemate.dto.admin.AdminDto;
import homemate.dto.user.UserDto;
import homemate.exception.BusinessLogicException;
import homemate.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final int PAGE = 0;
    private final int SIZE = 5;

    /**
     * 관리자 로그인
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody AdminDto.AdminRequestDto adminRequestDto, HttpServletRequest request) {


        try {
            AdminDto.AdminResponseDto loginResponse = adminService.login(adminRequestDto);

            HttpSession session = request.getSession();
            session.setAttribute("loginAdmin", loginResponse);

            // 세션 만료 시간 30분
            session.setMaxInactiveInterval(60 * 30);

            return ResponseEntity.ok("Login Successful!");
        } catch (BusinessLogicException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }


    /**
     * 관리자 로그아웃
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }







    /**
     * 관리자 조회
     */
    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(@RequestParam("adminId") Long adminId) {

        AdminDto.AdminResponseDto adminResponseDto = adminService.getAdmin(adminId);
        if (adminResponseDto != null) {
            return ResponseEntity.ok(adminResponseDto);
        } else {

            log.info("등록되지 않은 admin!");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 관리자 정보 수정
     */

    @PatchMapping("/update")
    public ResponseEntity<?> updateAdmin(@RequestParam("adminId") Long adminId, @RequestBody AdminDto.AdminPatchDto adminPatchDto) {
        return ResponseEntity.ok().body(adminService.updateAdmin(adminId, adminPatchDto));
    }

    /**
     * 관리자 - 전체 사용자 조회
     * 페이지 번호가 있으므로 cursor가 아닌 OFFSET으로 처리
     */
    @GetMapping("/user/chart")
    public ResponseEntity<?> getAllUser(){
        Page<UserDto.UserResponseDto> userList = adminService.getAllUser(PAGE, SIZE);
        return ResponseEntity.ok().body(userList);
    }

    /**
     * 관리자 - 특정 사용자 조회
     * @param userId
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<?> getDetailUser(@RequestParam("userId") Long userId){
        return ResponseEntity.ok().body(adminService.getDetailUser(userId));
    }

    /**
     * 관리자 - 사용자 정보 수정
     * @param userId
     * @return
     */

    @PatchMapping("/user")
    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.AdminPatchUserDto adminPatchUserDto){
        return ResponseEntity.ok().body(adminService.updateUser(userId, adminPatchUserDto));
    }

    /**
     * 관리자 - 사용자 삭제
     * @param userId
     * @return
     */

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId){
        adminService.deleteUser(userId);
        return ResponseEntity.ok().body("삭제된 유저 아이디: " + userId);
    }


}
