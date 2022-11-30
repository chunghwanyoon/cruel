package io.fana.cruel.app.v1.product.presentation

import io.fana.cruel.domain.product.application.GetProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "상품 API", description = "Product API")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val getProductService: GetProductService,
) {
    @Operation(operationId = "getAllProducts", description = "전체 활성화 상품 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    fun getAllProducts(): List<ProductResponse> = ProductResponse.of(getProductService.getAllActivatedProducts())

    @Operation(operationId = "getProduct", description = "특정 상품 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable("productId") productId: Long,
    ): ProductResponse = ProductResponse.of(getProductService.getProductById(productId))
}
