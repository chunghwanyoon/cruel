package io.fana.cruel.domain.schedule.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class RepaymentValidateFailedException(
    message: String?,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.REPAYMENT_VALIDATE_FAILED, message, cause)
