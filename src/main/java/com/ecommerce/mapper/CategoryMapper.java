package com.ecommerce.mapper;

import com.ecommerce.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类
     */
    List<Category> selectAllCategories();

    /**
     * 查询顶级分类
     */
    List<Category> selectTopCategories();

    /**
     * 根据父级ID查询子分类
     */
    List<Category> selectChildrenByParentId(@Param("parentId") Long parentId);

    /**
     * 根据ID查询分类
     */
    Category selectCategoryById(@Param("id") Long id);

    /**
     * 根据级别查询分类
     */
    List<Category> selectCategoriesByLevel(@Param("level") Integer level);

    /**
     * 根据名称搜索分类
     */
    List<Category> selectCategoriesByName(@Param("name") String name);

    /**
     * 插入分类
     */
    int insertCategory(Category category);

    /**
     * 更新分类
     */
    int updateCategory(Category category);

    /**
     * 删除分类
     */
    int deleteCategory(@Param("id") Long id);
}