package homemate.repository.user;

import homemate.constant.SocialType;
import homemate.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> getAllUser();

    @Query("SELECT u FROM UserEntity u")
    Page<UserEntity> getAllUser(Pageable pageable);



    UserEntity findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<UserEntity> findByEmail(String email);


    Optional<UserEntity> findByNickName(String nickName);


    Optional<UserEntity> findByRefreshToken(String refreshToken);

    boolean existsByNickName(String nickName);
}
