package com.ecommerce.controller;

import com.ecommerce.dto.Result;
import com.ecommerce.entity.Category;
import com.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制器
 *
 * @author 系统生成
 * @version 1.0.0
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "商品分类管理", description = "商品分类相关API")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取所有分类
     */
    @GetMapping
    @Operation(summary = "获取所有分类", description = "获取所有启用的商品分类，支持层级结构")
    public Result<List<Category>> getCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取分类失败: " + e.getMessage());
        }
    }

    /**
     * 获取顶级分类
     */
    @GetMapping("/top")
    @Operation(summary = "获取顶级分类", description = "获取所有顶级分类（父级ID为0）")
    public Result<List<Category>> getTopCategories() {
        try {
            List<Category> categories = categoryService.getTopCategories();
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取顶级分类失败: " + e.getMessage());
        }
    }

    /**
     * 根据父级ID获取子分类
     */
    @GetMapping("/children/{parentId}")
    @Operation(summary = "获取子分类", description = "根据父级分类ID获取所有子分类")
    public Result<List<Category>> getChildrenCategories(
            @io.swagger.v3.oas.annotations.Parameter(description = "父级分类ID，0表示顶级分类")
            @PathVariable Long parentId) {
        try {
            List<Category> categories = categoryService.getChildrenCategories(parentId);
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取子分类失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取分类详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取分类详情", description = "根据分类ID获取分类详细信息")
    public Result<Category> getCategory(
            @io.swagger.v3.oas.annotations.Parameter(description = "分类ID")
            @PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return Result.error("分类不存在");
            }
            return Result.success(category);
        } catch (Exception e) {
            return Result.error("获取分类详情失败: " + e.getMessage());
        }
    }

    /**
     * 根据级别获取分类
     */
    @GetMapping("/level/{level}")
    @Operation(summary = "按级别获取分类", description = "根据分类级别获取分类列表")
    public Result<List<Category>> getCategoriesByLevel(
            @io.swagger.v3.oas.annotations.Parameter(description = "分类级别：1-一级，2-二级，3-三级")
            @PathVariable Integer level) {
        try {
            List<Category> categories = categoryService.getCategoriesByLevel(level);
            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取分类失败: " + e.getMessage());
        }
    }
}