package homemate.controller.area;

import homemate.dto.area.AreaDto;
import homemate.service.area.AreaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("area")
@Slf4j
public class AreaController {

    private final AreaService areaService;

    /**
     * 행정구역 조회 (서울/경기)
     */

    //TODO: 행정구역 조회 굳이 필요하려나..? 일단 작성했음
    @GetMapping("/get")
    public ResponseEntity<?> getArea(@RequestParam("areaId") Long areaId) {

        AreaDto.AreaResponseDto areaResponseDto = areaService.getArea(areaId);
        if (areaResponseDto != null) {
            return ResponseEntity.ok(areaResponseDto);
        } else {
            //TODO: 나중에 에러처리 변수 따로 설정 후 수정할 것
            log.info("등록되지 않은 admin!");
            return ResponseEntity.notFound().build();
        }
    }

}
