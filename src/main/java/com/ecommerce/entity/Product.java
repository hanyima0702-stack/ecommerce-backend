package com.ecommerce.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class Product {

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
     * 品牌ID
     */
    private Long brandId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 重量（kg）
     */
    private BigDecimal weight;

    /**
     * 主图URL
     */
    private String imageUrl;

    /**
     * 商品图片列表（JSON格式）
     */
    private String images;

    /**
     * 商品编码
     */
    private String sku;

    /**
     * 商品标签，逗号分隔
     */
    private String tags;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 是否推荐：0-否，1-是
     */
    private Integer isFeatured;

    /**
     * 是否新品：0-否，1-是
     */
    private Integer isNew;

    /**
     * 是否热销：0-否，1-是
     */
    private Integer isHot;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    // 关联属性（不对应数据库字段）

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 平均评分
     */
    private BigDecimal averageRating;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    /**
     * 折扣比例
     */
    private BigDecimal discountRate;

    /**
     * 获取折扣比例
     */
    public BigDecimal getDiscountRate() {
        if (originalPrice != null && originalPrice.compareTo(BigDecimal.ZERO) > 0) {
            return BigDecimal.ONE.subtract(price.divide(originalPrice, 2, BigDecimal.ROUND_HALF_UP));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 判断是否有库存
     */
    public boolean hasStock() {
        return stock != null && stock > 0;
    }

    /**
     * 判断是否上架
     */
    public boolean isOnSale() {
        return Integer.valueOf(1).equals(status);
    }
}