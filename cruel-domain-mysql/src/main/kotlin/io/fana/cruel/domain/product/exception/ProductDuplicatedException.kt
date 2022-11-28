package io.fana.cruel.domain.product.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.InvalidValueException

class ProductDuplicatedException(
    message: String,
    cause: Throwable? = null,
) : InvalidValueException(ErrorCode.PRODUCT_CODE_DUPLICATED, message, cause) {
    companion object {
        fun ofCode(code: String) = ProductDuplicatedException("상품 코드가 중복되었습니다. code=$code")
    }
}
