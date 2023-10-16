package homemate.service.area;
import homemate.domain.area.AreaEntity;
import homemate.dto.area.AreaDto;
import homemate.mapper.area.AreaMapper;
import homemate.repository.area.AreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AreaService {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public AreaDto.AreaResponseDto getArea(Long areaId) {
        //Entity 조회
        AreaEntity areaEntity = areaRepository.findById(areaId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 area ID: " + areaId));

        //Entity를 DTO로 변환 후 return
        return areaMapper.toResponseDto(areaEntity);
    }


}
