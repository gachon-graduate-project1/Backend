package homemate.domain.building;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 매물 정보 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingEntity {

    @Id
    @Column(name = "building_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
//    private District district;

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

    private String transactioonType; //거래유형

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "building_images",
            joinColumns = @JoinColumn(name = "building_id")
    )
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>(); //매물 사진

    public void updateBuildingImages(List<String> noticeImages) {
        this.images = noticeImages;
    }
}

