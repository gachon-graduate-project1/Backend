package homemate.controller.admin;
import homemate.dto.admin.AdminDto;
import homemate.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;


    /**
     * 관리자 조회
     */
    @GetMapping("/get")
    public ResponseEntity<?> getAdmin(@RequestParam("adminId") Long adminId) {

        AdminDto.AdminResponseDto adminResponseDto = adminService.getAdmin(adminId);
        if (adminResponseDto != null) {
            return ResponseEntity.ok(adminResponseDto);
        } else {
            //TODO: 나중에 에러처리 변수 따로 설정 후 수정할 것
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


}
