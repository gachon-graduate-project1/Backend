package homemate.service.area;
import homemate.domain.admin.AdminEntity;
import homemate.domain.area.BuildingEntity;
import homemate.dto.area.BuildingDto;
import homemate.mapper.area.BuildingMapper;
import homemate.repository.admin.AdminRepository;
import homemate.repository.area.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final AdminRepository adminRepository;


    @Transactional
    public BuildingDto.BuildingResponseDto createBuilding(Long adminId, BuildingDto.BuildingRequestDto buildingRequestDto) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 admin ID: " + adminId));

        BuildingEntity savedBuilding = buildingRepository.save(buildingMapper.toReqeustEntity(buildingRequestDto, adminEntity));
        BuildingDto.BuildingResponseDto responseDto = buildingMapper.toResponseDto(savedBuilding);


        return responseDto;
    }


    public BuildingDto.BuildingResponseDto getBuilding(Long buildingId) {
        //Entity 조회
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 building: " + buildingId));

        //Entity를 DTO로 변환 후 return
        return buildingMapper.toResponseDto(buildingEntity);

    }

    @Transactional
    public void deleteBuilding(Long buildingId) {
        buildingRepository.deleteById(buildingId);
        log.info("삭제된 building: {}",buildingId);
    }
    

    //TODO : 이미지 수정 코드 작성 (클라우드 스토리지 정한 후)
//    @Transactional
//    public List<String> updateBuildingImages(Long BuildingId, List<MultipartFile> imageList) {
//        BuildingEntity buildingEntity = buildingRepository.findById(BuildingId)
//                .orElseThrow(()->new NoSuchElementException("등록되지 않은 Building: " + BuildingId));
//
//        //TODO : 기존이미지 삭제 코드 작성 
//        buildingEntity.getImages().forEach(s3Service::deleteFile);
//
//        //TODO : 새로운 이미지 업로드
//       
//
//        return ;
//    }


    /**
     * 매물 전체 검색 시 (주소에서 결과 나오게)
     */
    @Transactional
    public List<BuildingDto.BuildingResponseDto> search(String keyword) {

        List<BuildingEntity> buildingEntities = buildingRepository.findKeyword(keyword);
        List<BuildingDto.BuildingResponseDto> buildingResponseDtos = new ArrayList<>();


        for (BuildingEntity BuildingEntity :buildingEntities){
            BuildingDto.BuildingResponseDto buildingResponseDto = buildingMapper.toResponseDto(BuildingEntity);
             buildingResponseDtos.add(buildingResponseDto);
        }

        return buildingResponseDtos;
    }


}
