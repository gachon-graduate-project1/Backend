package homemate.repository.user;

import homemate.domain.area.BuildingEntity;
import homemate.domain.user.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
}
