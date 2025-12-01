package com.ecommerce.controller;

import com.ecommerce.dto.Result;
import com.ecommerce.dto.PageResult;
import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import com.ecommerce.dto.CartItemDTO;
import com.ecommerce.dto.AddToCartDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车控制器
 *
 * @author 系统生成
 * @version 1.0.0
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Validated
@Tag(name = "购物车管理", description = "购物车相关API")
public class CartController {

    private final CartService cartService;

    /**
     * 获取用户购物车列表
     */
    @GetMapping
    @Operation(summary = "获取购物车列表", description = "获取当前用户的购物车商品列表")
    public Result<Cart> getCart() {
        try {
            Cart cart = cartService.getCurrentUserCart();
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("获取购物车失败: " + e.getMessage());
        }
    }

    /**
     * 添加商品到购物车
     */
    @PostMapping("/items")
    @Operation(summary = "添加商品到购物车", description = "将指定商品添加到用户购物车")
    public Result<Cart> addToCart(@Valid @RequestBody AddToCartDTO addToCartDTO) {
        try {
            Cart cart = cartService.addToCart(addToCartDTO);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("添加到购物车失败: " + e.getMessage());
        }
    }

    /**
     * 更新购物车商品数量
     */
    @PutMapping("/items/{itemId}")
    @Operation(summary = "更新购物车商品数量", description = "更新购物车中指定商品的数量")
    public Result<Cart> updateCartItem(
            @Parameter(description = "购物车商品ID") @PathVariable Long itemId,
            @Parameter(description = "商品数量") @Min(value = 1, message = "商品数量不能小于1") @RequestParam Integer quantity) {
        try {
            Cart cart = cartService.updateCartItemQuantity(itemId, quantity);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("更新购物车失败: " + e.getMessage());
        }
    }

    /**
     * 删除购物车商品
     */
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "删除购物车商品", description = "从购物车中删除指定商品")
    public Result<Cart> removeCartItem(
            @Parameter(description = "购物车商品ID") @PathVariable Long itemId) {
        try {
            Cart cart = cartService.removeCartItem(itemId);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("删除购物车商品失败: " + e.getMessage());
        }
    }

    /**
     * 批量删除购物车商品
     */
    @PostMapping("/items/batch-delete")
    @Operation(summary = "批量删除购物车商品", description = "批量删除购物车中的多个商品")
    public Result<Cart> removeCartItems(@RequestBody List<Long> itemIds) {
        try {
            Cart cart = cartService.removeCartItems(itemIds);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("批量删除购物车商品失败: " + e.getMessage());
        }
    }

    /**
     * 清空购物车
     */
    @DeleteMapping
    @Operation(summary = "清空购物车", description = "清空当前用户的购物车")
    public Result<Void> clearCart() {
        try {
            cartService.clearCart();
            return Result.success();
        } catch (Exception e) {
            return Result.error("清空购物车失败: " + e.getMessage());
        }
    }

    /**
     * 获取购物车商品数量
     */
    @GetMapping("/count")
    @Operation(summary = "获取购物车商品数量", description = "获取当前用户购物车中的商品总数量")
    public Result<Integer> getCartItemCount() {
        try {
            Integer count = cartService.getCartItemCount();
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("获取购物车商品数量失败: " + e.getMessage());
        }
    }

    /**
     * 选择购物车商品（用于结算）
     */
    @PutMapping("/items/{itemId}/select")
    @Operation(summary = "选择购物车商品", description = "选择或取消选择购物车中的商品")
    public Result<Cart> selectCartItem(
            @Parameter(description = "购物车商品ID") @PathVariable Long itemId,
            @Parameter(description = "是否选择") @NotNull @RequestParam Boolean selected) {
        try {
            Cart cart = cartService.selectCartItem(itemId, selected);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("选择购物车商品失败: " + e.getMessage());
        }
    }

    /**
     * 全选/取消全选购物车商品
     */
    @PutMapping("/select-all")
    @Operation(summary = "全选/取消全选", description = "选择或取消选择购物车中的所有商品")
    public Result<Cart> selectAllCartItems(
            @Parameter(description = "是否全选") @NotNull @RequestParam Boolean selected) {
        try {
            Cart cart = cartService.selectAllCartItems(selected);
            return Result.success(cart);
        } catch (Exception e) {
            return Result.error("全选操作失败: " + e.getMessage());
        }
    }
}