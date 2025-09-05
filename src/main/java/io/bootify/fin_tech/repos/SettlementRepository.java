package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
