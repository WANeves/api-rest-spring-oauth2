package zup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zup.common.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
