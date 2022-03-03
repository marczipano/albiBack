package hu.albi.back.repo;

import java.util.Optional;

import hu.albi.back.model.ERole;
import hu.albi.back.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
