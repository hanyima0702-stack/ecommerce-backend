package com.ecommerce.mapper;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车Mapper接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Mapper
public interface CartMapper {

    /**
     * 根据用户ID获取购物车
     */
    Cart selectCartByUserId(@Param("userId") Long userId);

    /**
     * 插入购物车
     */
    int insertCart(Cart cart);

    /**
     * 更新购物车
     */
    int updateCart(Cart cart);

    /**
     * 根据购物车ID获取购物车商品列表
     */
    List<CartItem> selectCartItemsByCartId(@Param("cartId") Long cartId);

    /**
     * 插入购物车商品
     */
    int insertCartItem(CartItem cartItem);

    /**
     * 更新购物车商品
     */
    int updateCartItem(CartItem cartItem);

    /**
     * 根据ID获取购物车商品
     */
    CartItem selectCartItemById(@Param("id") Long id);

    /**
     * 根据购物车ID和商品ID获取购物车商品
     */
    CartItem selectCartItemByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    /**
     * 根据购物车ID和SKU获取购物车商品
     */
    CartItem selectCartItemByCartIdAndSku(@Param("cartId") Long cartId, @Param("sku") String sku);

    /**
     * 删除购物车商品
     */
    int deleteCartItem(@Param("id") Long id);

    /**
     * 批量删除购物车商品
     */
    int deleteCartItems(@Param("ids") List<Long> ids);

    /**
     * 根据购物车ID删除所有购物车商品
     */
    int deleteCartItemsByCartId(@Param("cartId") Long cartId);

    /**
     * 根据用户ID删除购物车
     */
    int deleteCartByUserId(@Param("userId") Long userId);

    /**
     * 更新购物车商品选择状态
     */
    int updateCartItemSelection(@Param("id") Long id, @Param("selected") Boolean selected);

    /**
     * 根据购物车ID更新所有商品选择状态
     */
    int updateAllCartItemSelections(@Param("cartId") Long cartId, @Param("selected") Boolean selected);

    /**
     * 获取用户购物车商品数量
     */
    Integer selectCartItemCountByUserId(@Param("userId") Long userId);

    /**
     * 获取用户选中商品数量
     */
    Integer selectSelectedItemCountByUserId(@Param("userId") Long userId);
}