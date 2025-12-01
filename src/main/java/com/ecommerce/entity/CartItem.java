package com.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 购物车商品实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车商品ID
     */
    private Long id;

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品SKU
     */
    private String sku;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图片URL
     */
    private String productImageUrl;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品原价
     */
    private BigDecimal productOriginalPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品重量
     */
    private BigDecimal productWeight;

    /**
     * 商品规格（JSON格式存储颜色、尺寸等）
     */
    private Map<String, String> variant;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品总价（商品价格 × 数量）
     */
    private BigDecimal totalPrice;

    /**
     * 是否选择（用于结算）
     */
    private Boolean selected = true;

    /**
     * 商品状态：0-下架，1-上架
     */
    private Integer productStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 购物车商品状态：0-删除，1-正常
     */
    private Integer status = 1;

    /**
     * 计算总价
     */
    public void calculateTotalPrice() {
        if (productPrice != null && quantity != null && quantity > 0) {
            totalPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            totalPrice = BigDecimal.ZERO;
        }
    }

    /**
     * 是否缺货
     */
    public Boolean isOutOfStock() {
        return productStatus != 1 || (productStock != null && productStock <= 0);
    }

    /**
     * 是否有效（商品上架且有库存）
     */
    public Boolean isValid() {
        return !isOutOfStock();
    }

    /**
     * 获取规格字符串
     */
    public String getVariantString() {
        if (variant == null || variant.isEmpty()) {
            return "";
        }

        return variant.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
}