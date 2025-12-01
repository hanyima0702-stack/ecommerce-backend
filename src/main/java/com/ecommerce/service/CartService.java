package com.ecommerce.service;

import com.ecommerce.dto.AddToCartDTO;
import com.ecommerce.entity.Cart;

import java.util.List;

/**
 * 购物车服务接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
public interface CartService {

    /**
     * 获取当前用户的购物车
     */
    Cart getCurrentUserCart();

    /**
     * 添加商品到购物车
     */
    Cart addToCart(AddToCartDTO addToCartDTO);

    /**
     * 更新购物车商品数量
     */
    Cart updateCartItemQuantity(Long itemId, Integer quantity);

    /**
     * 删除购物车商品
     */
    Cart removeCartItem(Long itemId);

    /**
     * 批量删除购物车商品
     */
    Cart removeCartItems(List<Long> itemIds);

    /**
     * 清空购物车
     */
    void clearCart();

    /**
     * 获取购物车商品数量
     */
    Integer getCartItemCount();

    /**
     * 选择购物车商品
     */
    Cart selectCartItem(Long itemId, Boolean selected);

    /**
     * 全选/取消全选购物车商品
     */
    Cart selectAllCartItems(Boolean selected);
}