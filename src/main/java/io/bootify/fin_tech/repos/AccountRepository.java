package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.Account;
import io.bootify.fin_tech.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findFirstByUser(User user);

}
