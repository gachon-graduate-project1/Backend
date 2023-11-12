package homemate.controller.building;

import homemate.config.S3.S3Service;
import homemate.dto.building.BuildingDto;
import homemate.service.building.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("building")
public class BuildingController {

    private final BuildingService buildingService;
    private S3Service s3Service;

    /**
     * 관리자 매물 등록 기능
     */
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

    /**
     * 매물 사진 수정
     */

    @PostMapping(value = "/updateBuildingImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBuildingImages(@RequestParam Long buildingId, @RequestPart List<MultipartFile> imageList) {
        return ResponseEntity.ok().body(buildingService.updateBuildingImages(buildingId, imageList));
    }






}
