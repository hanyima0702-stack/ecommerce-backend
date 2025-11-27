package com.ecommerce.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品响应DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 折扣比例
     */
    private BigDecimal discountRate;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 主图URL
     */
    private String imageUrl;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 商品标签
     */
    private String[] tags;

    /**
     * 平均评分
     */
    private BigDecimal averageRating;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    /**
     * 是否推荐
     */
    private Boolean isFeatured;

    /**
     * 是否新品
     */
    private Boolean isNew;

    /**
     * 是否热销
     */
    private Boolean isHot;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 是否有库存
     */
    public boolean hasStock() {
        return stock != null && stock > 0;
    }

    /**
     * 是否在促销
     */
    public boolean isOnSale() {
        return originalPrice != null && originalPrice.compareTo(price) > 0;
    }
}