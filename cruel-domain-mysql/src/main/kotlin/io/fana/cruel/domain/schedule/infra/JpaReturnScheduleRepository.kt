package io.fana.cruel.domain.schedule.infra

import io.fana.cruel.domain.schedule.application.SummarizedRepaymentInformation
import io.fana.cruel.domain.schedule.domain.ReturnSchedule
import io.fana.cruel.domain.schedule.domain.ReturnScheduleRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaReturnScheduleRepository : ReturnScheduleRepository, JpaRepository<ReturnSchedule, Long> {
    override fun getAllByOrderId(orderId: Long): List<ReturnSchedule>

    override fun getReturnScheduleById(scheduleId: Long): ReturnSchedule

    @Query(
        """
            SELECT
                SUM(`rs`.`principal`) AS `totalPrincipal`,
                SUM(`rs`.`interest`) AS `totalInterest`,
                SUM(`rs`.`principal`) + SUM(`rs`.`interest`) AS `totalAmount`,
            FROM return_schedules `rs`
            INNER JOIN orders `o` ON `o`.`id` = `rs`.`order_id`
            WHERE `o`.`user_id` = :userId
            AND `rs`.`is_returned` = FALSE
            GROUP BY `rs`.`scheduled_at`
        """,
        nativeQuery = true
    )
    override fun getSummarizedReturnInformation(userId: Long): List<SummarizedRepaymentInformation>

    override fun save(returnSchedule: ReturnSchedule): ReturnSchedule
}
