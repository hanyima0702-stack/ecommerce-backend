package com.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购物车商品列表
     */
    private List<CartItem> items;

    /**
     * 商品总数量
     */
    private Integer itemCount;

    /**
     * 商品小计
     */
    private BigDecimal subtotal;

    /**
     * 运费
     */
    private BigDecimal shippingFee = BigDecimal.ZERO;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 计算购物车统计信息
     */
    public void calculateTotals() {
        if (items == null || items.isEmpty()) {
            itemCount = 0;
            subtotal = BigDecimal.ZERO;
            totalAmount = shippingFee.subtract(discountAmount);
            return;
        }

        itemCount = 0;
        subtotal = BigDecimal.ZERO;

        for (CartItem item : items) {
            if (item.getSelected() != null && item.getSelected()) {
                itemCount += item.getQuantity();
                if (item.getTotalPrice() != null) {
                    subtotal = subtotal.add(item.getTotalPrice());
                }
            }
        }

        totalAmount = subtotal.add(shippingFee).subtract(discountAmount);
    }

    /**
     * 获取选中的商品数量
     */
    public Integer getSelectedItemCount() {
        if (items == null || items.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (CartItem item : items) {
            if (item.getSelected() != null && item.getSelected()) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    /**
     * 获取选中的商品
     */
    public List<CartItem> getSelectedItems() {
        if (items == null || items.isEmpty()) {
            return List.of();
        }

        return items.stream()
                .filter(item -> item.getSelected() != null && item.getSelected())
                .toList();
    }
}