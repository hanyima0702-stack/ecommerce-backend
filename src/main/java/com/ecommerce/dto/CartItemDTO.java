package com.ecommerce.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 购物车商品DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class CartItemDTO implements Serializable {

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
     * 商品规格（如颜色、尺寸等）
     */
    private Map<String, String> variant;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 是否选择
     */
    private Boolean selected = true;

    /**
     * 商品状态：0-下架，1-上架
     */
    private Integer productStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 购物车商品状态
     */
    private Integer status;

    /**
     * 商品分类名称
     */
    private String categoryName;

    /**
     * 商品品牌名称
     */
    private String brandName;

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
}