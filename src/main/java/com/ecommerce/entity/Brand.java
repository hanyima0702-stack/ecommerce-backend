package com.ecommerce.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 品牌实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌ID
     */
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌Logo
     */
    private String logo;

    /**
     * 品牌描述
     */
    private String description;

    /**
     * 排序值
     */
    private Integer sortOrder;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 是否启用
     */
    public Boolean isEnabled() {
        return status != null && status == 1;
    }

    /**
     * 是否禁用
     */
    public Boolean isDisabled() {
        return status != null && status == 0;
    }
}