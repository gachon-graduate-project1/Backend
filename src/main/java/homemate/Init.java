package homemate;

import homemate.constant.SocialType;
import homemate.domain.admin.AdminEntity;
import homemate.domain.building.BuildingEntity;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.repository.admin.AdminRepository;
import homemate.repository.building.BuildingRepository;
import homemate.repository.user.ArticleRepository;
import homemate.repository.user.CommentRepository;
import homemate.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init {

    //init.

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    //private final BuildingRepository buildingRepository;

    @PostConstruct
    private void initFirst(){
        initAdmins();
        initUsers();
        //initBuildings();
        initArticles();
        initComments();
    }

    @Transactional
    public void initAdmins() {

        AdminEntity admin = new AdminEntity();
        admin.setAdminName("admin");
        admin.setPassword("homemate");
        adminRepository.save(admin);


    }

    @Transactional
    public void initUsers() {
        for (int i = 0; i < 30; i++) {
            UserEntity user = new UserEntity();
            user.setUserName("user" + (i+1));
            user.setNickName("userNickname" + (i+1));
            user.setPassword("userPs" + (i+1));
            user.setEmail("user" + (i+1) + "@example.com");
            user.setSocialType(SocialType.KAKAO);
            user.setSocialId("userSocialId" + (i+1));
            user.setRefreshToken("userRefreshToken" + (i+1));
            userRepository.save(user);
        }
    }



//    @Transactional
//    public void initBuildings() {
//        for (int i = 0; i < 10; i++) {
//            BuildingEntity building = new BuildingEntity();
//            building.setBuildingName("가천건물" + i);
//            building.setAddress("광희동1가");
//            building.setBuildingField("원룸");
//            building.setContent("건물" + i + "입니다.");
//            building.setFloor(i + "층");
//            building.setMoveInDate("2024-01-0" + i);
//            building.setCheckDuplex("복층 없음");
//            building.setDirection("남동");
//            building.setNumberOfRoom("방" + i + "개");
//            building.setNumberOfParking("주차 가능 " + i + "대");
//            building.setRealterName("가천중개사" + i);
//            building.setRealterNumber("010-0000-000" + i);
//            building.setTransactionType("전세");
//            buildingRepository.save(building);
//        }
//    }

    @Transactional
    public void initArticles() {
        List<UserEntity> user = userRepository.findAll();
        if (!user.isEmpty()) {
            ArticleEntity article = new ArticleEntity();
            article.setUser(user.get(0));
            article.setTitle("용산역 근처에 프린트 ?");
            article.setContent("용산역 근처에 프린트 가능한 곳 있나요 ? 댓글 남겨주세요!");
            article.setComplain(0);
            articleRepository.save(article);

            ArticleEntity article1 = new ArticleEntity();
            article1.setUser(user.get(1));
            article1.setTitle("가천대 카페 추천해주세요 ?");
            article1.setContent("가천대 근처 괜찮은 카페있나요 ? 추천부탁드러요");
            article1.setComplain(0);
            articleRepository.save(article1);

            ArticleEntity article2 = new ArticleEntity();
            article2.setUser(user.get(2));
            article2.setTitle("충무로 지역 문화 센터 알려주세요!");
            article2.setContent("내용은 제목이랑 같습니다 !");
            article2.setComplain(0);
            articleRepository.save(article2);

            ArticleEntity article3 = new ArticleEntity();
            article3.setUser(user.get(3));
            article3.setTitle("가천대역 xx 1년 거주 후기입니다.");
            article3.setContent("생각보다 너무 깨끗하고 집주인분이 너무 좋습니다 ! 추천드려요");
            article3.setComplain(0);
            articleRepository.save(article3);

            ArticleEntity article4 = new ArticleEntity();
            article4.setUser(user.get(4));
            article4.setTitle("신당동으로 이사가는데 질문있어요!");
            article4.setContent("주변에 맛집 리스트 알려주실분 계신가요 ? 그리고 1번 출구에서 제일 가까운 스터디카페가 어디인가요 ?");
            article4.setComplain(0);
            articleRepository.save(article4);

            for (int i = 5; i <= 29; i++) {
                ArticleEntity articleEx = new ArticleEntity();
                articleEx.setUser(user.get(i));
                articleEx.setTitle("안녕하세요 !");
                articleEx.setContent("반갑습니다.");
                articleEx.setComplain(0);
                articleRepository.save(articleEx);
            }


        }
    }


    @Transactional
    public void initComments(){
        List<UserEntity> user = userRepository.findAll();
        List<ArticleEntity> article = articleRepository.findAll();
        if (!user.isEmpty()) {
            CommentEntity comment = new CommentEntity();
            comment.setUser(user.get(1));
            comment.setArticle(article.get(0));
            comment.setContent("1번 출구 3분거리에 있어요!");
            comment.setComplain(0);
            commentRepository.save(comment);

            CommentEntity comment1 = new CommentEntity();
            comment1.setUser(user.get(2));
            comment1.setArticle(article.get(0));
            comment1.setContent("안녕프린트라고 있어요!");
            comment1.setComplain(0);
            commentRepository.save(comment1);

            CommentEntity comment2 = new CommentEntity();
            comment2.setUser(user.get(3));
            comment2.setArticle(article.get(1));
            comment2.setContent("이디야 있어요!!");
            comment2.setComplain(0);
            commentRepository.save(comment2);

            CommentEntity comment3 = new CommentEntity();
            comment3.setUser(user.get(5));
            comment3.setArticle(article.get(2));
            comment3.setContent("저도 궁금해요!");
            comment3.setComplain(0);
            commentRepository.save(comment3);

            CommentEntity comment4 = new CommentEntity();
            comment4.setUser(user.get(5));
            comment4.setArticle(article.get(3)); ////
            comment4.setContent("겨울에 관리비 어느정도 나오나요?");
            comment4.setComplain(0);
            commentRepository.save(comment4);

            CommentEntity comment5 = new CommentEntity();
            comment5.setUser(user.get(2));
            comment5.setArticle(article.get(3));
            comment5.setContent("치안은 괜찮나요??");
            comment5.setComplain(0);
            commentRepository.save(comment5);

            CommentEntity comment6 = new CommentEntity();
            comment6.setUser(user.get(10));
            comment6.setArticle(article.get(3));
            comment6.setContent("저도 여기사는데 완전 추천이요!");
            comment6.setComplain(0);
            commentRepository.save(comment6);

            for (int i = 7; i <= 29; i++) {
                CommentEntity commentEx = new CommentEntity();
                commentEx.setUser(user.get(2));
                commentEx.setArticle(article.get(i));
                commentEx.setContent("반갑습니다!!!");
                commentEx.setComplain(0);
                commentRepository.save(commentEx);
            }


        }
    }
}