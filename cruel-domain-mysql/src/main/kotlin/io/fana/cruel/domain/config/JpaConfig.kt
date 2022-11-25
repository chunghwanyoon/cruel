package io.fana.cruel.domain.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["io.fana.cruel.domain"])
@EnableJpaRepositories(
    basePackages = ["io.fana.cruel.domain"],
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean::class
)
class JpaConfig
