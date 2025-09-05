package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.Role;
import io.bootify.fin_tech.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByUserRoleRoles(Role role);

    List<User> findAllByUserRoleRoles(Role role);

}
