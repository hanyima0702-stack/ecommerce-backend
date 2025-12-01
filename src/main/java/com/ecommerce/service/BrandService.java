package com.ecommerce.service;

import com.ecommerce.entity.Brand;

import java.util.List;

/**
 * 品牌服务接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
public interface BrandService {

    /**
     * 获取所有品牌
     */
    List<Brand> getAllBrands();

    /**
     * 根据ID获取品牌
     */
    Brand getBrandById(Long id);

    /**
     * 获取推荐品牌
     */
    List<Brand> getFeaturedBrands(Integer limit);

    /**
     * 搜索品牌
     */
    List<Brand> searchBrands(String keyword);

    /**
     * 按排序获取品牌
     */
    List<Brand> getSortedBrands(String sortBy, String sortOrder);
}