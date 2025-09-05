package io.bootify.fin_tech.repos;

import io.bootify.fin_tech.domain.KycDocument;
import io.bootify.fin_tech.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KycDocumentRepository extends JpaRepository<KycDocument, Long> {

    KycDocument findFirstByUser(User user);

}
