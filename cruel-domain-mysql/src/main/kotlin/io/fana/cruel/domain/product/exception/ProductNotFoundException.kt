package io.fana.cruel.domain.product.exception

import io.fana.cruel.core.exception.ErrorCode
import io.fana.cruel.core.exception.client.NotFoundException

class ProductNotFoundException(
    message: String,
    cause: Throwable? = null,
) : NotFoundException(ErrorCode.PRODUCT_NOT_FOUND, message, cause) {
    companion object {
        fun ofId(productId: Long) = ProductNotFoundException("상품을 찾을 수 없습니다. id=$productId")
    }
}
