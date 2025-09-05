package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
