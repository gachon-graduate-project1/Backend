package homemate.dto.area;
import homemate.constant.BuildingField;
import homemate.constant.TransactionType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

public class BuildingDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BuildingPostDto {

        private String areaDetail;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String cost; //가격

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfRoom; // 방수/욕실수

        private String numberOfParking; //주차 가능 수

        private String realterName; //중개소이름

        private String realterNumber; //중개소번호

        @NotBlank
        private BuildingField buildingField; //매물종류

        @NotBlank
        private String buildingName;  //매물이름

        @NotBlank
        private TransactionType transactioonType; //거래유형


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BuildingResponseDto {

        private AreaDto.AreaResponseDto area;

        private String areaDetail;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String cost; //가격

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfRoom; // 방수/욕실수

        private String numberOfParking; //주차 가능 수

        private String realterName; //중개소이름

        private String realterNumber; //중개소번호

        private BuildingField buildingField; //매물종류

        private String buildingName;  //매물이름

        private TransactionType transactioonType; //거래유형

        private List<String> images; //매물 사진

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BuildingPatchDto {

        private String areaDetail;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String cost; //가격

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfRoom; // 방수/욕실수

        private String numberOfParking; //주차 가능 수

        private String realterName; //중개소이름

        private String realterNumber; //중개소번호

        @NotBlank
        private BuildingField buildingField; //매물종류

        @NotBlank
        private String buildingName;  //매물이름

        @NotBlank
        private TransactionType transactioonType; //거래유형

        private List<String> images; //매물 사진

    }

}
