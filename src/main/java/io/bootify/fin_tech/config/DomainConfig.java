package io.bootify.fin_tech.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.fin_tech.domain")
@EnableJpaRepositories("io.bootify.fin_tech.repos")
@EnableTransactionManagement
public class DomainConfig {
}
