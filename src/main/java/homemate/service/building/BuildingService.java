package homemate.service.building;

import homemate.config.S3.S3Service;
import homemate.domain.admin.AdminEntity;
import homemate.domain.building.BuildingEntity;
import homemate.dto.building.BuildingDto;
import homemate.exception.BusinessLogicException;
import homemate.exception.ExceptionCode;
import homemate.mapper.building.BuildingMapper;
import homemate.repository.admin.AdminRepository;
import homemate.repository.building.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final AdminRepository adminRepository;
    private final S3Service s3Service;


    @Transactional
    public BuildingDto.BuildingResponseDto createBuilding(Long adminId, BuildingDto.BuildingRequestDto buildingRequestDto) {

        AdminEntity adminEntity = adminRepository.findById(adminId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ADMIN_NOT_FOUND));

        BuildingEntity savedBuilding = buildingRepository.save(buildingMapper.toReqeustEntity(buildingRequestDto, adminEntity));
        BuildingDto.BuildingResponseDto responseDto = buildingMapper.toResponseDto(savedBuilding);


        return responseDto;
    }


    public BuildingDto.BuildingResponseDto getBuilding(Long buildingId) {
        //Entity 조회
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUILDING_IS_NOT_EXIST));

        //Entity를 DTO로 변환 후 return
        return buildingMapper.toResponseDto(buildingEntity);

    }

    public BuildingDto.BuildingResponseDto getBuilding(String buildingName) {
        //Entity 조회
        BuildingEntity buildingEntity = buildingRepository.findByBuildingName(buildingName)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUILDING_IS_NOT_EXIST));

        //Entity를 DTO로 변환 후 return
        return buildingMapper.toResponseDto(buildingEntity);
    }




    @Transactional
    public BuildingDto.BuildingResponseDto updateBuilding(Long buildingId, BuildingDto.BuildingPatchDto buildingPatchDto) {


        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.BUILDING_IS_NOT_EXIST));

        buildingMapper.updateFromPatchDto(buildingPatchDto,buildingEntity);

        //TODO : 나중에 에러 처리하게 코드 변경
        return buildingMapper.toResponseDto(buildingEntity);
    }

    @Transactional
    public void deleteBuilding(Long buildingId) {
        buildingRepository.deleteById(buildingId);
        log.info("삭제된 building: {}",buildingId);
    }

    @Transactional
    public List<String> updateBuildingImages(Long buildingId, List<MultipartFile> imageList) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(()->new BusinessLogicException(ExceptionCode.BUILDING_IS_NOT_EXIST));

        //기존 이미지 삭제
        buildingEntity.getImages().forEach(s3Service::deleteFile);

        //새로운 이미지 업로드
        List<String> newImageUrls = s3Service.uploadFileList(imageList);
        buildingEntity.updateBuildingImages(newImageUrls);

        return newImageUrls;
    }


    /**
     * 매물 검색 시 (주소에서 결과 나오게)
     */
    @Transactional
    public List<BuildingDto.BuildingResponseDto> searchBuilding(String keyword) {

        List<BuildingEntity> buildingEntities = buildingRepository.findKeyword(keyword);
        List<BuildingDto.BuildingResponseDto> buildingResponseDtos = new ArrayList<>();


        if (buildingEntities.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.KEYWORD_IS_NOT_EXIST);
        }


        for (BuildingEntity BuildingEntity :buildingEntities){
            BuildingDto.BuildingResponseDto buildingResponseDto = buildingMapper.toResponseDto(BuildingEntity);
             buildingResponseDtos.add(buildingResponseDto);
        }

        return buildingResponseDtos;
    }

    /**
     * 전체 매물 조회
     */
    @Transactional
    public List<BuildingDto.BuildingResponseDto> getAllBuilding(){

        List<BuildingEntity> buildingEntities = buildingRepository.getAllBuilding();
        List<BuildingDto.BuildingResponseDto> buildingResponseDtos = new ArrayList<>();

        for (BuildingEntity buildingEntity : buildingEntities) {
            BuildingDto.BuildingResponseDto buildingResponseDto = buildingMapper.toResponseDto(buildingEntity);
            buildingResponseDtos.add(buildingResponseDto);
        }

        return buildingResponseDtos;
    }


}
