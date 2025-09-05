package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.Account;
import io.bootify.fin_tech.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findFirstByFromAccount(Account account);

    Transaction findFirstByToAccount(Account account);

}
