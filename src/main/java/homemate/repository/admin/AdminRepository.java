package homemate.repository.admin;

import homemate.domain.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {

    Optional<AdminEntity> findByAdminName(String adminName);
}
