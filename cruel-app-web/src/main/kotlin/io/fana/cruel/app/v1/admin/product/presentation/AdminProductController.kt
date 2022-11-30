package io.fana.cruel.app.v1.admin.product.presentation

import io.fana.cruel.domain.product.application.CreateProductRequest
import io.fana.cruel.domain.product.application.CreateProductService
import io.fana.cruel.domain.product.application.DeleteProductService
import io.fana.cruel.domain.product.application.GetProductService
import io.fana.cruel.domain.product.application.UpdateProductRequest
import io.fana.cruel.domain.product.application.UpdateProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/admin/products")
class AdminProductController(
    private val getProductService: GetProductService,
    private val createProductService: CreateProductService,
    private val updateProductService: UpdateProductService,
    private val deleteProductService: DeleteProductService,
) {
    @GetMapping
    @Operation(operationId = "getAllProducts", summary = "전체 상품 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    fun getAllProducts(): List<AdminProductResponse> = AdminProductResponse.of(getProductService.getAllProducts())

    @GetMapping("/{productId}")
    @Operation(operationId = "getProductById", summary = "특정 상품 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    fun getProductById(
        @PathVariable productId: Long,
    ): AdminProductResponse = AdminProductResponse.of(getProductService.getProductById(productId))

    @PostMapping
    @Operation(operationId = "createProduct", summary = "상품 등록")
    @ApiResponse(responseCode = "201", description = "등록 성공")
    fun createProduct(
        @RequestBody request: CreateProductRequest,
    ): AdminProductResponse {
        return AdminProductResponse.of(createProductService.createProduct(request))
    }

    @PutMapping("/{productId}")
    @Operation(operationId = "updateProduct", summary = "상품 정보 수정")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    fun updateProduct(
        @PathVariable("productId") productId: Long,
        @RequestBody request: UpdateProductRequest,
    ): AdminProductResponse {
        return AdminProductResponse.of(updateProductService.update(productId, request))
    }

    @DeleteMapping("/{productId}")
    @Operation(operationId = "deleteProduct", summary = "상품 정보 삭제")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    fun deleteProduct(
        @PathVariable("productId") productId: Long,
    ) = deleteProductService.delete(productId)
}
