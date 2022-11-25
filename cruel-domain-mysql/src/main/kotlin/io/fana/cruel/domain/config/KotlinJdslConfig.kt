package io.fana.cruel.domain.config

import com.linecorp.kotlinjdsl.QueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class KotlinJdslConfig(
    @PersistenceContext val entityManager: EntityManager,
) {
    @Bean
    fun jpaQueryFactory() = QueryFactoryImpl(
        criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
        subqueryCreator = SubqueryCreatorImpl(),
    )
}
