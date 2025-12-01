package com.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 添加到购物车DTO
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class AddToCartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 商品数量
     */
    @Min(value = 1, message = "商品数量不能小于1")
    private Integer quantity = 1;

    /**
     * 商品规格（如颜色、尺寸等）
     */
    private Map<String, String> variant;

    /**
     * 商品SKU
     */
    private String sku;

    /**
     * 备注
     */
    private String remarks;
}