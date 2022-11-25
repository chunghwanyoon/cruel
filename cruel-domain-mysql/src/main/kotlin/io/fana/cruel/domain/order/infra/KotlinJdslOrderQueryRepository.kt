package io.fana.cruel.domain.order.infra

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import io.fana.cruel.core.type.DelayStatus
import io.fana.cruel.core.type.DelayStatusSearchFilter
import io.fana.cruel.core.type.OrderStatus
import io.fana.cruel.core.type.OrderStatusSearchFilter
import io.fana.cruel.domain.order.domain.Order
import io.fana.cruel.domain.order.domain.OrderQueryRepository
import org.springframework.stereotype.Repository

@Repository
class KotlinJdslOrderQueryRepository(
    private val queryFactory: QueryFactory,
) : OrderQueryRepository {
    override fun findOrderByStatusAndDelayStatusFilters(
        statusFilter: OrderStatusSearchFilter,
        delayedFilter: DelayStatusSearchFilter,
        lastSeen: Long,
        limit: Int,
    ): List<Order> {
        return queryFactory.listQuery {
            select(entity(Order::class))
            from(entity(Order::class))
            where(
                and(
                    filterBy(statusFilter, delayedFilter),
                    col(Order::id).lessThan(lastSeen),
                )
            )
            orderBy(col(Order::id).desc())
            limit(limit)
        }
    }

    private fun <T> CriteriaQueryDsl<T>.filterBy(
        statusFilter: OrderStatusSearchFilter,
        delayedFilter: DelayStatusSearchFilter,
    ): PredicateSpec {
        val delayQuery = when (delayedFilter) {
            DelayStatusSearchFilter.NORMAL -> and(col(Order::delayStatus).equal(DelayStatus.NORMAL))
            DelayStatusSearchFilter.DELAYED -> and(col(Order::delayStatus).equal(DelayStatus.DELAYED))
            DelayStatusSearchFilter.ALL -> or(
                col(Order::delayStatus).equal(DelayStatus.NORMAL),
                col(Order::delayStatus).equal(DelayStatus.DELAYED),
            )
        }
        val statusQuery = when (statusFilter) {
            OrderStatusSearchFilter.ALL -> or(
                col(Order::status).equal(OrderStatus.CREATED),
                col(Order::status).equal(OrderStatus.CANCELED),
                col(Order::status).equal(OrderStatus.COMPLETED),
                col(Order::status).equal(OrderStatus.REJECTED),
                col(Order::status).equal(OrderStatus.APPROVED),
            )

            else -> and(col(Order::status).equal(OrderStatus.valueOf(statusFilter.name)))
        }
        return and(delayQuery, statusQuery)
    }
}
