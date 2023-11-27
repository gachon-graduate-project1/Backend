package homemate.repository.user;

import homemate.domain.user.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {

    @Query("SELECT a FROM ArticleEntity a WHERE a.title LIKE %:keyword% ")
    List<ArticleEntity> findKeyword(String keyword);



    @Query("SELECT a FROM ArticleEntity a")
    Page<ArticleEntity> getAllArticle(Pageable pageable);

    @Query("SELECT a FROM ArticleEntity a ORDER BY a.id DESC")
    List<ArticleEntity> getAllArticle();


}
