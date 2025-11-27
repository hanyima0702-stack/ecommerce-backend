package com.ecommerce.controller;

import com.ecommerce.dto.PageResult;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductQueryDTO;
import com.ecommerce.dto.Result;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品控制器
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
@Tag(name = "商品管理", description = "商品相关接口")
public class ProductController {

    private final ProductService productService;

    /**
     * 分页查询商品列表
     */
    @GetMapping
    @Operation(summary = "分页查询商品列表", description = "支持关键词搜索、分类筛选、价格区间筛选等多种查询条件")
    public Result<PageResult<ProductDTO>> pageProducts(@Valid ProductQueryDTO query) {
        log.info("分页查询商品列表，查询条件：{}", query);

        PageResult<ProductDTO> result = productService.pageProducts(query);

        log.info("分页查询商品列表成功，总数：{}", result.getTotal());
        return Result.success(result);
    }

    /**
     * 根据ID查询商品详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询商品详情", description = "根据商品ID获取商品的详细信息")
    public Result<ProductDTO> getProductById(
            @Parameter(description = "商品ID", required = true)
            @PathVariable @NotNull(message = "商品ID不能为空") @Min(value = 1, message = "商品ID必须大于0") Long id) {

        log.info("根据ID查询商品详情，商品ID：{}", id);

        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            log.warn("商品不存在，商品ID：{}", id);
            return Result.notFound("商品不存在");
        }

        log.info("查询商品详情成功，商品ID：{}", id);
        return Result.success(product);
    }

    /**
     * 根据SKU查询商品
     */
    @GetMapping("/sku/{sku}")
    @Operation(summary = "根据SKU查询商品", description = "根据商品SKU获取商品信息")
    public Result<ProductDTO> getProductBySku(
            @Parameter(description = "商品SKU", required = true)
            @PathVariable @NotNull(message = "商品SKU不能为空") String sku) {

        log.info("根据SKU查询商品，SKU：{}", sku);

        ProductDTO product = productService.getProductBySku(sku);
        if (product == null) {
            log.warn("商品不存在，SKU：{}", sku);
            return Result.notFound("商品不存在");
        }

        log.info("查询商品成功，SKU：{}", sku);
        return Result.success(product);
    }

    /**
     * 搜索商品
     */
    @GetMapping("/search")
    @Operation(summary = "搜索商品", description = "根据关键词搜索商品")
    public Result<PageResult<ProductDTO>> searchProducts(
            @Parameter(description = "搜索关键词", required = true)
            @RequestParam @NotNull(message = "搜索关键词不能为空") String keyword,

            @Parameter(description = "页码")
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码不能小于1") Integer pageNum,

            @Parameter(description = "每页大小")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页大小不能小于1") Integer pageSize) {

        log.info("搜索商品，关键词：{}，页码：{}，每页大小：{}", keyword, pageNum, pageSize);

        PageResult<ProductDTO> result = productService.searchProducts(keyword, pageNum, pageSize);

        log.info("搜索商品成功，关键词：{}，总数：{}", keyword, result.getTotal());
        return Result.success(result);
    }

    /**
     * 获取推荐商品
     */
    @GetMapping("/featured")
    @Operation(summary = "获取推荐商品", description = "获取平台推荐的商品列表")
    public Result<List<ProductDTO>> getFeaturedProducts(
            @Parameter(description = "限制数量")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "限制数量不能小于1") Integer limit) {

        log.info("获取推荐商品，限制数量：{}", limit);

        List<ProductDTO> products = productService.getFeaturedProducts(limit);

        log.info("获取推荐商品成功，数量：{}", products.size());
        return Result.success(products);
    }

    /**
     * 获取新品商品
     */
    @GetMapping("/new")
    @Operation(summary = "获取新品商品", description = "获取平台新品商品列表")
    public Result<List<ProductDTO>> getNewProducts(
            @Parameter(description = "限制数量")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "限制数量不能小于1") Integer limit) {

        log.info("获取新品商品，限制数量：{}", limit);

        List<ProductDTO> products = productService.getNewProducts(limit);

        log.info("获取新品商品成功，数量：{}", products.size());
        return Result.success(products);
    }

    /**
     * 获取热销商品
     */
    @GetMapping("/hot")
    @Operation(summary = "获取热销商品", description = "获取平台热销商品列表")
    public Result<List<ProductDTO>> getHotProducts(
            @Parameter(description = "限制数量")
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "限制数量不能小于1") Integer limit) {

        log.info("获取热销商品，限制数量：{}", limit);

        List<ProductDTO> products = productService.getHotProducts(limit);

        log.info("获取热销商品成功，数量：{}", products.size());
        return Result.success(products);
    }

    /**
     * 获取相关商品
     */
    @GetMapping("/{id}/related")
    @Operation(summary = "获取相关商品", description = "根据商品分类获取相关商品推荐")
    public Result<List<ProductDTO>> getRelatedProducts(
            @Parameter(description = "商品ID", required = true)
            @PathVariable @NotNull(message = "商品ID不能为空") @Min(value = 1, message = "商品ID必须大于0") Long id,

            @Parameter(description = "限制数量")
            @RequestParam(defaultValue = "5") @Min(value = 1, message = "限制数量不能小于1") Integer limit) {

        log.info("获取相关商品，商品ID：{}，限制数量：{}", id, limit);

        // 先获取商品详情以获得分类ID
        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            log.warn("商品不存在，商品ID：{}", id);
            return Result.notFound("商品不存在");
        }

        List<ProductDTO> relatedProducts = productService.getRelatedProducts(
            product.getCategoryId(), id, limit);

        log.info("获取相关商品成功，商品ID：{}，数量：{}", id, relatedProducts.size());
        return Result.success(relatedProducts);
    }

    /**
     * 批量获取商品
     */
    @PostMapping("/batch")
    @Operation(summary = "批量获取商品", description = "根据商品ID列表批量获取商品信息")
    public Result<List<ProductDTO>> getProductsByIds(
            @Parameter(description = "商品ID列表", required = true)
            @RequestBody @NotNull(message = "商品ID列表不能为空") List<@NotNull(message = "商品ID不能为空") Long> ids) {

        log.info("批量获取商品，商品ID列表：{}", ids);

        if (ids.isEmpty()) {
            log.warn("商品ID列表为空");
            return Result.badRequest("商品ID列表不能为空");
        }

        List<ProductDTO> products = productService.getProductsByIds(ids);

        log.info("批量获取商品成功，请求数量：{}，返回数量：{}", ids.size(), products.size());
        return Result.success(products);
    }

    /**
     * 验证商品是否存在且有库存
     */
    @GetMapping("/{id}/validate")
    @Operation(summary = "验证商品", description = "验证商品是否存在且有库存")
    public Result<Boolean> validateProduct(
            @Parameter(description = "商品ID", required = true)
            @PathVariable @NotNull(message = "商品ID不能为空") @Min(value = 1, message = "商品ID必须大于0") Long id) {

        log.info("验证商品，商品ID：{}", id);

        boolean isValid = productService.validateProductExists(id);

        log.info("商品验证完成，商品ID：{}，结果：{}", id, isValid);
        return Result.success(isValid);
    }
}