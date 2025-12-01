package com.ecommerce.controller;

import com.ecommerce.dto.Result;
import com.ecommerce.entity.Brand;
import com.ecommerce.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品品牌控制器
 *
 * @author 系统生成
 * @version 1.0.0
 */
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Tag(name = "商品品牌管理", description = "商品品牌相关API")
public class BrandController {

    private final BrandService brandService;

    /**
     * 获取所有品牌
     */
    @GetMapping
    @Operation(summary = "获取所有品牌", description = "获取所有启用的商品品牌")
    public Result<List<Brand>> getBrands() {
        try {
            List<Brand> brands = brandService.getAllBrands();
            return Result.success(brands);
        } catch (Exception e) {
            return Result.error("获取品牌失败: " + e.getMessage());
        }
    }

    /**
     * 获取推荐品牌
     */
    @GetMapping("/featured")
    @Operation(summary = "获取推荐品牌", description = "获取推荐的品牌列表")
    public Result<List<Brand>> getFeaturedBrands(
            @io.swagger.v3.oas.annotations.Parameter(description = "返回数量限制")
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<Brand> brands = brandService.getFeaturedBrands(limit);
            return Result.success(brands);
        } catch (Exception e) {
            return Result.error("获取推荐品牌失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取品牌详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取品牌详情", description = "根据品牌ID获取品牌详细信息")
    public Result<Brand> getBrand(
            @io.swagger.v3.oas.annotations.Parameter(description = "品牌ID")
            @PathVariable Long id) {
        try {
            Brand brand = brandService.getBrandById(id);
            if (brand == null) {
                return Result.error("品牌不存在");
            }
            return Result.success(brand);
        } catch (Exception e) {
            return Result.error("获取品牌详情失败: " + e.getMessage());
        }
    }

    /**
     * 搜索品牌
     */
    @GetMapping("/search")
    @Operation(summary = "搜索品牌", description = "根据关键词搜索品牌")
    public Result<List<Brand>> searchBrands(
            @io.swagger.v3.oas.annotations.Parameter(description = "搜索关键词")
            @RequestParam String keyword) {
        try {
            List<Brand> brands = brandService.searchBrands(keyword);
            return Result.success(brands);
        } catch (Exception e) {
            return Result.error("搜索品牌失败: " + e.getMessage());
        }
    }

    /**
     * 按排序获取品牌
     */
    @GetMapping("/sorted")
    @Operation(summary = "按排序获取品牌", description = "根据排序字段获取品牌列表")
    public Result<List<Brand>> getSortedBrands(
            @io.swagger.v3.oas.annotations.Parameter(description = "排序字段：name, created_time")
            @RequestParam(defaultValue = "sort_order") String sortBy,
            @io.swagger.v3.oas.annotations.Parameter(description = "排序方向：asc, desc")
            @RequestParam(defaultValue = "asc") String sortOrder) {
        try {
            List<Brand> brands = brandService.getSortedBrands(sortBy, sortOrder);
            return Result.success(brands);
        } catch (Exception e) {
            return Result.error("获取品牌失败: " + e.getMessage());
        }
    }
}