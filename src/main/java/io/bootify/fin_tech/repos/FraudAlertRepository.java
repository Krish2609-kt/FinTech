package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.FraudAlert;
import io.bootify.fin_tech.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {

    FraudAlert findFirstByTransaction(Transaction transaction);

}
