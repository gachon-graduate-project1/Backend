package homemate.dto.building;
import homemate.constant.District;
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
    public static class BuildingRequestDto {

        private District district;

        private String address;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String warantPrice; //보증금

        private String dealPrice; //매매가

        private String rentPrice; //임대료

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfParking; //주차 가능 수

        @NotBlank
        private String realterName; //중개소이름

        @NotBlank
        private String realterNumber; //중개소번호

        @NotBlank
        private String buildingField; //매물종류

        @NotBlank
        private String buildingName;  //매물이름

        @NotBlank
        private String numberOfRoom; // 방수/욕실수

        @NotBlank
        private TransactionType transactioonType;

        private List<String> images; //매물 사진



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BuildingResponseDto {


        private String address;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String warantPrice; //보증금

        private String dealPrice; //매매가

        private String rentPrice; //임대료

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfParking; //주차 가능 수

        private String realterName; //중개소이름

        private String realterNumber; //중개소번호

        private String buildingField; //매물종류

        private String buildingName;  //매물이름

        private String numberOfRoom; // 방수/욕실수

        private List<String> images; //매물 사진

        private TransactionType transactioonType;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BuildingPatchDto {

        private District district;

        private String address;  //소재지(매물 세부 주소)

        private String content; //매물특징

        private String floor; // 층수

        private String warantPrice; //보증금

        private String dealPrice; //매매가

        private String rentPrice; //임대료

        private String moveInDate; //입주가능일

        private String checkDuplex; //복층여부

        private String direction; //방향

        private String numberOfParking; //주차 가능 수

        @NotBlank
        private String realterName; //중개소이름

        @NotBlank
        private String realterNumber; //중개소번호

        @NotBlank
        private String buildingField; //매물종류

        @NotBlank
        private String buildingName;  //매물이름

        @NotBlank
        private String numberOfRoom; // 방수/욕실수

        @NotBlank
        private TransactionType transactioonType;

        private List<String> images; //매물 사진

    }

}
