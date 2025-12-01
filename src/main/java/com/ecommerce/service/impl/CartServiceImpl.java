package com.ecommerce.service.impl;

import com.ecommerce.dto.AddToCartDTO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.mapper.CartMapper;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.service.CartService;
import com.ecommerce.util.SecurityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Cart getCurrentUserCart() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 获取或创建购物车
        Cart cart = cartMapper.selectCartByUserId(userId);
        if (cart == null) {
            // 创建新购物车
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItemCount(0);
            cart.setSubtotal(java.math.BigDecimal.ZERO);
            cart.setTotalAmount(java.math.BigDecimal.ZERO);
            cart.setCreatedTime(LocalDateTime.now());
            cart.setUpdatedTime(LocalDateTime.now());
            cartMapper.insertCart(cart);
        }

        // 获取购物车商品
        List<CartItem> items = cartMapper.selectCartItemsByCartId(cart.getId());
        cart.setItems(items);

        // 计算总价
        cart.calculateTotals();

        return cart;
    }

    @Override
    @Transactional
    public Cart addToCart(AddToCartDTO addToCartDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 验证商品
        Product product = productMapper.selectProductById(addToCartDTO.getProductId());
        if (product == null || product.getStatus() != 1) {
            throw new BusinessException("商品不存在或已下架");
        }

        if (product.getStock() < addToCartDTO.getQuantity()) {
            throw new BusinessException("商品库存不足");
        }

        // 获取用户购物车
        Cart cart = getCurrentUserCart();

        // 检查是否已存在相同规格的商品
        CartItem existingItem = null;
        if (addToCartDTO.getSku() != null) {
            existingItem = cartMapper.selectCartItemByCartIdAndSku(cart.getId(), addToCartDTO.getSku());
        } else {
            existingItem = cartMapper.selectCartItemByCartIdAndProductId(cart.getId(), addToCartDTO.getProductId());
        }

        if (existingItem != null) {
            // 更新数量
            int newQuantity = existingItem.getQuantity() + addToCartDTO.getQuantity();
            if (newQuantity > product.getStock()) {
                throw new BusinessException("商品库存不足");
            }
            existingItem.setQuantity(newQuantity);
            existingItem.calculateTotalPrice();
            existingItem.setUpdatedTime(LocalDateTime.now());
            cartMapper.updateCartItem(existingItem);
        } else {
            // 创建新的购物车商品
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(addToCartDTO.getProductId());
            cartItem.setSku(addToCartDTO.getSku() != null ? addToCartDTO.getSku() : product.getSku());
            cartItem.setProductName(product.getName());
            cartItem.setProductDescription(product.getDescription());
            cartItem.setProductImageUrl(product.getImageUrl());
            cartItem.setProductPrice(product.getPrice());
            cartItem.setProductOriginalPrice(product.getOriginalPrice());
            cartItem.setProductStock(product.getStock());
            cartItem.setProductWeight(product.getWeight());
            cartItem.setVariant(addToCartDTO.getVariant());
            cartItem.setQuantity(addToCartDTO.getQuantity());
            cartItem.setRemarks(addToCartDTO.getRemarks());
            cartItem.setSelected(true);
            cartItem.setProductStatus(product.getStatus());
            cartItem.setStatus(1);
            cartItem.setCreatedTime(LocalDateTime.now());
            cartItem.setUpdatedTime(LocalDateTime.now());

            cartItem.calculateTotalPrice();
            cartMapper.insertCartItem(cartItem);
        }

        // 重新计算购物车总额
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        // 返回更新后的购物车
        return getCurrentUserCart();
    }

    @Override
    @Transactional
    public Cart updateCartItemQuantity(Long itemId, Integer quantity) {
        if (quantity < 1) {
            throw new BusinessException("商品数量不能小于1");
        }

        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 获取购物车商品
        CartItem cartItem = cartMapper.selectCartItemById(itemId);
        if (cartItem == null) {
            throw new BusinessException("购物车商品不存在");
        }

        // 验证商品库存
        Product product = productMapper.selectProductById(cartItem.getProductId());
        if (product != null && product.getStock() < quantity) {
            throw new BusinessException("商品库存不足");
        }

        // 更新数量
        cartItem.setQuantity(quantity);
        cartItem.calculateTotalPrice();
        cartItem.setUpdatedTime(LocalDateTime.now());
        cartMapper.updateCartItem(cartItem);

        // 重新计算购物车总额
        Cart cart = getCurrentUserCart();
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        return getCurrentUserCart();
    }

    @Override
    @Transactional
    public Cart removeCartItem(Long itemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        CartItem cartItem = cartMapper.selectCartItemById(itemId);
        if (cartItem == null) {
            throw new BusinessException("购物车商品不存在");
        }

        // 验证权限
        Cart cart = getCurrentUserCart();
        if (!cartItem.getCartId().equals(cart.getId())) {
            throw new BusinessException("无权限操作此购物车商品");
        }

        // 删除购物车商品
        cartMapper.deleteCartItem(itemId);

        // 重新计算购物车总额
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        return getCurrentUserCart();
    }

    @Override
    @Transactional
    public Cart removeCartItems(List<Long> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return getCurrentUserCart();
        }

        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        Cart cart = getCurrentUserCart();

        // 验证所有商品都属于当前用户的购物车
        List<CartItem> items = cartMapper.selectCartItemsByCartId(cart.getId());
        for (Long itemId : itemIds) {
            boolean belongsToCart = items.stream()
                    .anyMatch(item -> item.getId().equals(itemId));
            if (!belongsToCart) {
                throw new BusinessException("购物车商品不存在或无权限操作");
            }
        }

        // 批量删除
        cartMapper.deleteCartItems(itemIds);

        // 重新计算购物车总额
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        return getCurrentUserCart();
    }

    @Override
    @Transactional
    public void clearCart() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        Cart cart = getCurrentUserCart();

        // 删除所有购物车商品
        cartMapper.deleteCartItemsByCartId(cart.getId());

        // 重置购物车统计
        cart.setItemCount(0);
        cart.setSubtotal(java.math.BigDecimal.ZERO);
        cart.setTotalAmount(java.math.BigDecimal.ZERO);
        cart.setUpdatedTime(LocalDateTime.now());
        cartMapper.updateCart(cart);
    }

    @Override
    public Integer getCartItemCount() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return 0;
        }

        return cartMapper.selectCartItemCountByUserId(userId);
    }

    @Override
    @Transactional
    public Cart selectCartItem(Long itemId, Boolean selected) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        CartItem cartItem = cartMapper.selectCartItemById(itemId);
        if (cartItem == null) {
            throw new BusinessException("购物车商品不存在");
        }

        // 验证权限
        Cart cart = getCurrentUserCart();
        if (!cartItem.getCartId().equals(cart.getId())) {
            throw new BusinessException("无权限操作此购物车商品");
        }

        // 更新选择状态
        cartMapper.updateCartItemSelection(itemId, selected);

        // 重新计算购物车总额
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        return getCurrentUserCart();
    }

    @Override
    @Transactional
    public Cart selectAllCartItems(Boolean selected) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        Cart cart = getCurrentUserCart();

        // 更新所有商品选择状态
        cartMapper.updateAllCartItemSelections(cart.getId(), selected);

        // 重新计算购物车总额
        cart.setUpdatedTime(LocalDateTime.now());
        cart.calculateTotals();
        cartMapper.updateCart(cart);

        return getCurrentUserCart();
    }
}