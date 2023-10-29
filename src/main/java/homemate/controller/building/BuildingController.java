package homemate.controller.building;

import homemate.dto.building.BuildingDto;
import homemate.dto.user.UserDto;
import homemate.service.building.BuildingService;
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
    public ResponseEntity<?> updateBuilding(@RequestParam("buildingId") Long buildingId,
                                            @RequestBody BuildingDto.BuildingPatchDto buildingPatchDto) {

        return ResponseEntity.ok().body(buildingService.updateBuilding(buildingId, buildingPatchDto));
    }


    //TODO: 이미지 수정 컨트롤러 작성
    //@PostMapping(value = "/updateImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBuilding(@RequestParam Long buildingId) {
        buildingService.deleteBuilding(buildingId);
        return ResponseEntity.ok().body("Deleted BuildingId : " + buildingId);
    }

    /**
     * 전체 매물 조회
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<BuildingDto.BuildingResponseDto>> getAllBuilding() {
        List<BuildingDto.BuildingResponseDto> buildings = buildingService.getAllBuilding();
        return ResponseEntity.ok().body(buildings);
    }

    /**
     * 매물 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<BuildingDto.BuildingResponseDto>> searchBuilding(@RequestParam String keyword) {
        List<BuildingDto.BuildingResponseDto> buildings = buildingService.searchBuilding(keyword);
        return ResponseEntity.ok().body(buildings);

    }






}
