package com.ecommerce.service.impl;

import com.ecommerce.entity.Brand;
import com.ecommerce.mapper.BrandMapper;
import com.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;

    @Override
    public List<Brand> getAllBrands() {
        return brandMapper.selectAllBrands();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandMapper.selectBrandById(id);
    }

    @Override
    public List<Brand> getFeaturedBrands(Integer limit) {
        return brandMapper.selectFeaturedBrands(limit);
    }

    @Override
    public List<Brand> searchBrands(String keyword) {
        return brandMapper.selectBrandsByName(keyword);
    }

    @Override
    public List<Brand> getSortedBrands(String sortBy, String sortOrder) {
        return brandMapper.selectBrandsSorted(sortBy, sortOrder);
    }
}