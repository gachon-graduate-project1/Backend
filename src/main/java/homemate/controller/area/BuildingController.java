package homemate.controller.area;

import homemate.dto.area.BuildingDto;
import homemate.service.area.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("building")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping("/create")
    public ResponseEntity<BuildingDto.BuildingResponseDto> createBuilding(@RequestParam("adminId") Long adminId, @RequestBody BuildingDto.BuildingRequestDto buildingRequestDto) {
        BuildingDto.BuildingResponseDto responseDto = buildingService.createBuilding(adminId, buildingRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getBuilding(@RequestParam("buildingId") Long buildingId) {
        BuildingDto.BuildingResponseDto building = buildingService.getBuilding(buildingId);
        return ResponseEntity.ok().body(building);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateBuilding(@RequestBody BuildingDto.BuildingPatchDto buildingPatchDto) {

        return ResponseEntity.ok().body(buildingService.updateBuilding(buildingPatchDto));
    }


    //TODO: 이미지 수정 컨트롤러 작성
    //@PostMapping(value = "/updateImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBuilding(@RequestParam Long buildingId) {
        buildingService.deleteBuilding(buildingId);
        return ResponseEntity.ok().body("삭제된 BuildingId : " + buildingId);
    }

    /**
     * 전체 매물 조회
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<BuildingDto.BuildingResponseDto>> getAllNotice() {
        List<BuildingDto.BuildingResponseDto> buildings = buildingService.getAllBuilding();
        return ResponseEntity.ok().body(buildings);
    }






}
