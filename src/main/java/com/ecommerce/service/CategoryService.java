package com.ecommerce.service;

import com.ecommerce.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
public interface CategoryService {

    /**
     * 获取所有分类
     */
    List<Category> getAllCategories();

    /**
     * 获取顶级分类
     */
    List<Category> getTopCategories();

    /**
     * 根据父级ID获取子分类
     */
    List<Category> getChildrenCategories(Long parentId);

    /**
     * 根据ID获取分类
     */
    Category getCategoryById(Long id);

    /**
     * 根据级别获取分类
     */
    List<Category> getCategoriesByLevel(Integer level);

    /**
     * 根据名称搜索分类
     */
    List<Category> searchCategories(String name);
}