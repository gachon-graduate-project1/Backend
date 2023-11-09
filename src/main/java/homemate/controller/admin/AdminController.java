package homemate.controller.admin;

import homemate.dto.admin.AdminDto;
import homemate.dto.building.BuildingDto;
import homemate.dto.user.ArticleDto;
import homemate.dto.user.UserDto;
import homemate.exception.BusinessLogicException;
import homemate.service.admin.AdminService;
import homemate.service.building.BuildingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    private final BuildingService buildingService;

    private final int PAGE = 0;
    private final int SIZE = 5;


    /**
     * 관리자 로그인 페이지로 이동
     */
    @GetMapping("/form")
    public String loginPage() {
        log.info("----------");


        return "adminLogin"; // adminLogin.html 페이지의 파일명
    }




    /**
         * 관리자 로그인
         */
//    @PostMapping("/login")
//    @ResponseBody
//    public ResponseEntity<?> login(@RequestBody AdminDto.AdminRequestDto adminRequestDto, HttpServletRequest request) {
//
//
//        try {
//            AdminDto.AdminResponseDto loginResponse = adminService.login(adminRequestDto);
//
//            HttpSession session = request.getSession();
//            session.setAttribute("loginAdmin", loginResponse);
//
//            // 세션 만료 시간 30분
//            session.setMaxInactiveInterval(60 * 30);
//
//            return ResponseEntity.ok("Login Successful!");
//        } catch (BusinessLogicException e) {
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//        }
//    }

    @PostMapping(value = "/login")
    public String login(AdminDto.AdminRequestDto adminRequestDto, HttpServletRequest request, Model model) {
        try {
            AdminDto.AdminResponseDto loginResponse = adminService.login(adminRequestDto);
            log.info(loginResponse.getAdminName());

            HttpSession session = request.getSession();
            session.setAttribute("loginAdmin", loginResponse);

            // 세션 만료 시간 30분
            session.setMaxInactiveInterval(60 * 30);

            return "redirect:/admin/user/chart"; // 로그인 성공 시 memberForm 페이지로 이동
        } catch (BusinessLogicException e) {
            model.addAttribute("error", "Login failed"); // 로그인 실패 시 에러 메시지를 모델에 추가
            return "redirect:/admin/form"; // 로그인 실패 시 adminLogin 페이지로 리다이렉트
        }
    }



    /**
     * 관리자 로그아웃
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/form";
    }




//
//
//
//    /**
//     * 관리자 조회
//     */
//    @GetMapping("/get")
//    public ResponseEntity<?> getAdmin(@RequestParam("adminId") Long adminId) {
//
//        AdminDto.AdminResponseDto adminResponseDto = adminService.getAdmin(adminId);
//        if (adminResponseDto != null) {
//            return ResponseEntity.ok(adminResponseDto);
//        } else {
//
//            log.info("등록되지 않은 admin!");
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    /**
//     * 관리자 정보 수정
//     */
//
//    @PatchMapping("/update")
//    public ResponseEntity<?> updateAdmin(@RequestParam("adminId") Long adminId, @RequestBody AdminDto.AdminPatchDto adminPatchDto) {
//        return ResponseEntity.ok().body(adminService.updateAdmin(adminId, adminPatchDto));
//    }
//
    /**
     * 관리자 - 전체 사용자 조회
     * 페이지 번호가 있으므로 cursor가 아닌 OFFSET으로 처리
     */

    @GetMapping("/user/chart")
    public String getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<UserDto.UserResponseDto> userList = adminService.getAllUser(page, size);

        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "memberForm";
    }

    @GetMapping("/building/chart")
    public String getAllBuilding(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<BuildingDto.BuildingResponseDto> buildingList = adminService.getAllBuilding(page, size);

        model.addAttribute("buildingList", buildingList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "buildingForm";
    }

    @GetMapping("/article/chart")
    public String getAllArticle(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        Page<ArticleDto.ArticleResponseDto> articleList = adminService.getAllArticle(page, size);

        model.addAttribute("articleList", articleList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "articleForm";
    }


//    @GetMapping("/user/chart")
//    public ResponseEntity<?> getAllUser(){
//        Page<UserDto.UserResponseDto> userList = adminService.getAllUser(PAGE, SIZE);
//        return ResponseEntity.ok().body(userList);
//    }

    /**
     * 관리자 페이지 검색 기능
     */

    /**
     * 관리자 - 특정 사용자 조회
     * @param userId
     * @return
     */
    @GetMapping("/getUser")
    public ResponseEntity<?> getDetailUser(@RequestParam("userId") Long userId){
        return ResponseEntity.ok().body(adminService.getDetailUser(userId));
    }

    /**
     * 관리자 - 사용자 정보 수정
     * @param userId
     * @return
     */

//    @PatchMapping("/updateUser")
//    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.AdminPatchUserDto adminPatchUserDto){
//        return ResponseEntity.ok().body(adminService.updateUser(userId, adminPatchUserDto));
//    }

    @PatchMapping("/updateUser")
    public String updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.AdminPatchUserDto adminPatchUserDto) {
        adminService.updateUser(userId, adminPatchUserDto);
        return "redirect:/admin/user/chart"; // 리다이렉트할 URL
    }


    @PatchMapping("/update/building")
    public String updateBuilding(@RequestParam("buildingId") Long buildingId,
                                            @RequestBody BuildingDto.BuildingPatchDto buildingPatchDto) {

        buildingService.updateBuilding(buildingId, buildingPatchDto);
        return "buildingUpdate";
    }

//
//    /**
//     * 관리자 - 사용자 삭제
//     * @param userId
//     * @return
//     */
//
//    @DeleteMapping("/user")
//    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId){
//        adminService.deleteUser(userId);
//        return ResponseEntity.ok().body("삭제된 유저 아이디: " + userId);
//    }


}
