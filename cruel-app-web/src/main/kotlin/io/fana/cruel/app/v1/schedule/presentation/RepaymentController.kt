package io.fana.cruel.app.v1.schedule.presentation

import io.fana.cruel.app.security.LoginUser
import io.fana.cruel.domain.schedule.application.GetReturnScheduleService
import io.fana.cruel.domain.schedule.application.SummarizedRepaymentInformation
import io.fana.cruel.domain.schedule.application.UpdateReturnScheduleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상환 API", description = "Repayment API")
@RestController
@RequestMapping("/api/v1/repayments")
class RepaymentController(
    private val getReturnScheduleService: GetReturnScheduleService,
    private val updateReturnScheduleService: UpdateReturnScheduleService,
) {
    @GetMapping
    @Operation(operationId = "getRepaymentSchedules", description = "상환 스케줄 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    fun getRepaymentSchedules(
        @RequestParam("orderId") orderId: Long,
        @Parameter(hidden = true)
        loginUser: LoginUser,
    ): List<ReturnScheduleResponse> {
        return ReturnScheduleResponse.of(getReturnScheduleService.getReturnSchedulesByOrderId(orderId))
    }

    @GetMapping("/summarized-info")
    @Operation(operationId = "getSummarizeRepaymentInfo", description = "다가오는 상환 정보 조회")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    fun getSummarizeRepaymentInfo(
        @Parameter(hidden = true)
        loginUser: LoginUser,
    ): SummarizedRepaymentInformation {
        return getReturnScheduleService.getSummarizedReturnInformation(loginUser.id)
    }

    @PostMapping("/{scheduleId}")
    @Operation(operationId = "repay", description = "상환")
    @ApiResponse(description = "요청 성공", responseCode = "201")
    fun repay(
        @Parameter(hidden = true)
        loginUser: LoginUser,
        @PathVariable("scheduleId") scheduleId: Long,
    ): ReturnScheduleResponse {
        return ReturnScheduleResponse.of(updateReturnScheduleService.repayReturnSchedule(scheduleId))
    }
}
