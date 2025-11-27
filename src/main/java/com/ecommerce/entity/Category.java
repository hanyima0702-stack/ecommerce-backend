package com.ecommerce.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品分类实体类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class Category {

    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID，0表示顶级分类
     */
    private Long parentId;

    /**
     * 分类级别：1-一级，2-二级，3-三级
     */
    private Integer level;

    /**
     * 排序值，越小越靠前
     */
    private Integer sortOrder;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 分类图片
     */
    private String imageUrl;

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

    // 关联属性
    /**
     * 子分类列表
     */
    private List<Category> children;

    /**
     * 是否启用
     */
    public boolean isEnabled() {
        return Integer.valueOf(1).equals(status);
    }

    /**
     * 是否为顶级分类
     */
    public boolean isTopLevel() {
        return parentId != null && parentId == 0;
    }

    /**
     * 是否有子分类
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }
}