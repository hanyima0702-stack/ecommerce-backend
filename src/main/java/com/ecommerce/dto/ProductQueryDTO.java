package com.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品查询DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class ProductQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品关键词（商品名称或描述）
     */
    private String keyword;

    /**
     * 分类ID列表
     */
    private List<Long> categoryIds;

    /**
     * 品牌ID列表
     */
    private List<Long> brandIds;

    /**
     * 最低价格
     */
    @Min(value = 0, message = "最低价格不能小于0")
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    @Min(value = 0, message = "最高价格不能小于0")
    private BigDecimal maxPrice;

    /**
     * 商品标签
     */
    private String tag;

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
     * 排序字段：price-价格，sales-销量，created_time-创建时间，rating-评分
     */
    private String sortBy = "created_time";

    /**
     * 排序方向：asc-升序，desc-降序
     */
    private String sortOrder = "desc";

    /**
     * 页码
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小不能小于1")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer pageSize = 10;

    /**
     * 商品状态：0-下架，1-上架，不传表示查询所有
     */
    private Integer status;

    /**
     * 获取排序SQL片段
     */
    public String getOrderByClause() {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "created_time DESC";
        }

        String column = switch (sortBy.toLowerCase()) {
            case "price" -> "price";
            case "sales" -> "sales";
            case "created_time", "createtime" -> "created_time";
            case "rating" -> "average_rating";
            default -> "created_time";
        };

        String direction = "desc".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";
        return column + " " + direction;
    }

    /**
     * 验证价格区间
     */
    public boolean isPriceRangeValid() {
        if (minPrice != null && maxPrice != null) {
            return minPrice.compareTo(maxPrice) <= 0;
        }
        return true;
    }
}