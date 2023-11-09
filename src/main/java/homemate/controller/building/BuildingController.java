package homemate.controller.building;

import homemate.domain.building.BuildingEntity;
import homemate.domain.user.UserEntity;
import homemate.dto.building.BuildingDto;
import homemate.dto.user.UserDto;
import homemate.repository.building.BuildingRepository;
import homemate.service.building.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final BuildingRepository buildingRepository;

    private final int PAGE = 0;
    private final int SIZE = 5;

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

//
//    public Page<UserDto.UserResponseDto> getAllUser(int page, int size){
//        // 페이지 설정
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<UserEntity> userEntities = userRepository.findAll(pageable);
//        // 페이지를 Dto로 변환
//        Page<UserDto.UserResponseDto> userList = userEntities.map(m ->
//                UserDto.UserResponseDto.builder()
//                        .id(m.getId())
//                        .userName(m.getUserName())
//                        .email(m.getEmail())
//                        .nickName(m.getNickName())
//                        .password(m.getPassword())
//                        .role(m.getRole())
//                        .socialId(m.getSocialId())
//                        .socialType(m.getSocialType())
//                        .refreshToken(m.getRefreshToken())
//                        .build());
//
//
//        return userList;
//    }

    /**
     * 전체 매물 조회 (페이징 처리)
     */
    @GetMapping("/getAll")
    public Page<BuildingDto.BuildingResponseDto> getAllBuilding(int page, int size) {
        // 페이지 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<BuildingEntity> buildingEntities = buildingRepository.findAll(pageable);
        // 페이지를 Dto로 변환
        Page<BuildingDto.BuildingResponseDto> buildingList = buildingEntities.map(m ->
                BuildingDto.BuildingResponseDto.builder()
                        .id(m.getId())
                        .address(m.getAddress())
                        .content(m.getContent())
                        .floor(m.getFloor())
                        .warantPrice(m.getWarantPrice())
                        .dealPrice(m.getDealPrice())
                        .rentPrice(m.getRentPrice())
                        .moveInDate(m.getMoveInDate())
                        .checkDuplex(m.getCheckDuplex())
                        .direction(m.getDirection())
                        .numberOfParking(m.getNumberOfParking())
                        .realterName(m.getRealterName())
                        .realterNumber(m.getRealterNumber())
                        .buildingField(m.getBuildingField())
                        .buildingName(m.getBuildingName())
                        .numberOfRoom(m.getNumberOfRoom())
                        .images(m.getImages())
                        .transactioonType(m.getTransactioonType())
                        .build());
        return buildingList;
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
