package homemate.dto.area;
import lombok.*;

public class AreaDto {

    /**
     * Area 수정하지 않음 (서울,경기 고정)
     */


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AreaPostDto {

        private String district;


    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AreaResponseDto {
        private Long id;

        private String district; //서울 or 경기

    }
}
