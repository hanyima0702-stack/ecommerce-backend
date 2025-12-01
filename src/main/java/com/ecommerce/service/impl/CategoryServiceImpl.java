package com.ecommerce.service.impl;

import com.ecommerce.entity.Category;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectAllCategories();
    }

    @Override
    public List<Category> getTopCategories() {
        return categoryMapper.selectTopCategories();
    }

    @Override
    public List<Category> getChildrenCategories(Long parentId) {
        return categoryMapper.selectChildrenByParentId(parentId);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> getCategoriesByLevel(Integer level) {
        return categoryMapper.selectCategoriesByLevel(level);
    }

    @Override
    public List<Category> searchCategories(String name) {
        return categoryMapper.selectCategoriesByName(name);
    }
}