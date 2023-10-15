package homemate.repository.area;
import homemate.domain.area.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<BuildingEntity,Long> {
}
