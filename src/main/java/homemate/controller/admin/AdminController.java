package homemate.controller.admin;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import homemate.domain.user.UserEntity;
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
    @RequestMapping("/form")
    public String loginPage() {
        log.info("------");

        return "adminLogin"; // adminLogin.html 페이지의 파일명
    }



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

    /**
     * 관리자 - 검색(사용자, 매물, 게시글)
     * 페이지 번호가 있으므로 cursor가 아닌 OFFSET으로 처리
     */

//    @GetMapping("/user/search")
//    public String getUserSearch(@RequestParam("nickName") String nickName, UserDto.UserResponseDto userResponseDto, Model model) {
//        userResponseDto = adminService.getDetailUser2(nickName);
//        model.addAttribute("userInfo", userResponseDto);
//        return "membderDetail"; // 회원 정보를 표시할 뷰 페이지
//    }


    @GetMapping("/user/search")
    public String searchUser(@RequestParam("nickName") String nickName, Model model) {
        UserDto.UserResponseDto user = adminService.getUserByNickname(nickName); // 사용자의 닉네임으로 검색
        model.addAttribute("searchForm", user);
        return "redirect:/admin/user/chart";
    }












//    /**
//     * 관리자 - 사용자 정보 수정
//     * @param userId
//     * @return
//     */
//
//    @PatchMapping("/user")
//    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.AdminPatchUserDto adminPatchUserDto){
//        return ResponseEntity.ok().body(adminService.updateUser(userId, adminPatchUserDto));
//    }
//
    /**
     * 관리자 - 수정(사용자, 매물, 게시글/댓글)
     */

//    사용자 닉네임만 수정 가능
//    @PostMapping(value = "/update/user")
//    public String updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.AdminPatchUserDto adminPatchUserDto) {
//        adminService.updateUser(userId, adminPatchUserDto);
//        return "redirect:/admin/user/chart";
//    }

    @PostMapping(value = "/update/user")
    public String updateUser(@RequestBody UserDto.AdminPatchUserDto adminPatchUserDto) {
        adminService.updateUser(adminPatchUserDto.getId(), adminPatchUserDto);
        return "redirect:/admin/user/chart";
    }




    /**
     * 관리자 -  삭제(사용자, 매물, 게시글)
     * @param userId
     * @return
     */

    @PostMapping("/delete/user")
    public String deleteUser(@RequestParam("userId") Long userId) {
        adminService.deleteUser(userId);
        return "redirect:/admin/user/chart";
    }

    @PostMapping("/delete/building")
    public String deleteBuilding(@RequestParam("buildingId") Long buildingId) {
        adminService.deleteBuilding(buildingId);
        return "redirect:/admin/building/chart";
    }

    @PostMapping("/delete/article")
    public String deleteArticle(@RequestParam("articleId") Long articleId) {
        adminService.deleteArticle(articleId);
        return "redirect:/admin/article/chart";
    }



}
