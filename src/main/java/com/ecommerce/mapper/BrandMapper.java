package com.ecommerce.mapper;

import com.ecommerce.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌Mapper接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Mapper
public interface BrandMapper {

    /**
     * 查询所有品牌
     */
    List<Brand> selectAllBrands();

    /**
     * 根据ID查询品牌
     */
    Brand selectBrandById(@Param("id") Long id);

    /**
     * 查询推荐品牌
     */
    List<Brand> selectFeaturedBrands(@Param("limit") Integer limit);

    /**
     * 根据名称搜索品牌
     */
    List<Brand> selectBrandsByName(@Param("name") String name);

    /**
     * 按排序查询品牌
     */
    List<Brand> selectBrandsSorted(@Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);

    /**
     * 插入品牌
     */
    int insertBrand(Brand brand);

    /**
     * 更新品牌
     */
    int updateBrand(Brand brand);

    /**
     * 删除品牌
     */
    int deleteBrand(@Param("id") Long id);
}