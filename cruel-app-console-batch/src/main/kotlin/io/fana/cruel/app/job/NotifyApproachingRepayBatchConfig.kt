package io.fana.cruel.app.job

import io.fana.cruel.app.common.logger
import io.fana.cruel.app.domain.ApproachingReturnSchedule
import io.fana.cruel.core.util.convertToLocalDate
import io.fana.cruel.domain.notification.domain.NotificationService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
class NotifyApproachingRepayBatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dataSource: DataSource,
    private val notificationService: NotificationService, // FIXME: SMSNotificationService 등의 구현체가 필요
) {
    private val log = logger()

    // Job ------------------------------------------------------------------------------------------------

    @Bean
    fun notifyApproachingRepayJob(): Job =
        jobBuilderFactory.get("notifyApproachingRepayJob")
            .start(notifyApproachingRepayStep())
            .build()

    // Step ------------------------------------------------------------------------------------------------

    @Bean
    @JobScope
    fun notifyApproachingRepayStep(): Step =
        stepBuilderFactory.get("notifyApproachingRepayStep")
            .chunk<ApproachingReturnSchedule, Unit>(CHUNK_SIZE)
            .reader(notifyApproachingRepayItemReader(null))
            .processor(notifyApproachingRepayItemProcessor(null))
            .writer(notifyApproachingRepayItemWriter(null))
            .build()

    // Reader ------------------------------------------------------------------------------------------------

    @Bean
    @StepScope
    fun notifyApproachingRepayItemReader(
        @Value("#{jobParameters[indexDate]}") indexDate: String?,
    ): JdbcCursorItemReader<ApproachingReturnSchedule> {
        requireNotNull(indexDate) { "indexDate is required!!" }

        log.info("notifyApproachingRepayItemReader called, indexDate = $indexDate")

        return JdbcCursorItemReaderBuilder<ApproachingReturnSchedule>()
            .name("notifyApproachingRepayItemReader")
            .fetchSize(CHUNK_SIZE)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper())
            .sql(
                """
                    SELECT
                        `u`.`user_id` AS `userId`,
                        `rs`.`seq_id` AS `seqId`,
                        `01012345678` AS `phoneNumber`,
                        `rs`.`scheduled_at` AS `scheduledAt`,
                        `rs`.`principal` AS `principal`,
                        `rs`.`interest` AS `interest`,
                        `rs`.`total_amount` AS `totalAmount`
                    FROM `return_schedules` `rs`
                    INNER JOIN `orders` `o` ON `rs`.`order_id` = `o`.`id`
                    INNER JOIN `users` `u` ON `o`.`user_id` = `u`.`id`
                    WHERE `rs`.`scheduledAt` = ?
                """.trimIndent()
            )
            .preparedStatementSetter {
                it.setString(
                    1,
                    convertToLocalDate(indexDate).plusDays(APPROACHING_REPAY_DAYS).toString()
                )
            }
            .build()
    }

    // Processor ------------------------------------------------------------------------------------------------

    @Bean
    @StepScope
    fun notifyApproachingRepayItemProcessor(
        @Value("#{jobParameters[indexDate]}") indexDate: String?,
    ): ItemProcessor<ApproachingReturnSchedule, Unit> = ItemProcessor<ApproachingReturnSchedule, Unit> {
        requireNotNull(indexDate) { "indexDate is required!!" }

        log.info(
            "try to send notification for approaching repayment user" +
                "userId={}, phoneNumber={}, scheduledAt={}" +
                "totalAmount={} principal={} interest={}",
            it.userId,
            it.phoneNumber,
            it.scheduledAt,
            it.totalAmount,
            it.principal,
            it.interest
        )
        notificationService.sendSMS(it.phoneNumber, "TEST_MESSAGE")
    }

    // Writer ------------------------------------------------------------------------------------------------

    @Bean
    @StepScope
    fun notifyApproachingRepayItemWriter(
        @Value("#{jobParameters[indexDate]}") indexDate: String?,
    ): ItemWriter<Unit> = ItemWriter<Unit> { items ->
        log.info("notifyApproachingRepayItemWriter called, indexDate = $indexDate, targetSize=${items.size}")
        log.info("notifyApproachingRepayItemWriter completed, actually do nothing")
    }

    companion object {
        private const val APPROACHING_REPAY_DAYS = 3L
    }
}
