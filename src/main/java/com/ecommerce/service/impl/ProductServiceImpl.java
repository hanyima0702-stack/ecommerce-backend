package com.ecommerce.service.impl;

import com.ecommerce.dto.PageResult;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductQueryDTO;
import com.ecommerce.entity.Product;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public PageResult<ProductDTO> pageProducts(ProductQueryDTO query) {
        log.info("分页查询商品列表，查询条件：{}", query);

        // 验证价格区间
        if (!query.isPriceRangeValid()) {
            log.warn("价格区间无效：minPrice={}, maxPrice={}", query.getMinPrice(), query.getMaxPrice());
            return PageResult.empty();
        }

        // 使用PageHelper进行分页
        PageHelper.startPage(query.getPageNum(), query.getPageSize());

        // 查询商品列表
        List<Product> productList = productMapper.selectProductList(query);

        // 获取分页信息
        PageInfo<Product> pageInfo = new PageInfo<>(productList);

        // 转换为DTO
        List<ProductDTO> productDTOList = convertToDTOList(productList);

        // 构建分页结果
        return PageResult.of(
            productDTOList,
            pageInfo.getTotal(),
            (long) pageInfo.getPageNum(),
            (long) pageInfo.getPageSize()
        );
    }

    @Override
    public ProductDTO getProductById(Long id) {
        log.info("根据ID查询商品详情，商品ID：{}", id);

        if (id == null || id <= 0) {
            log.warn("商品ID无效：{}", id);
            return null;
        }

        Product product = productMapper.selectProductById(id);
        if (product == null) {
            log.warn("商品不存在，商品ID：{}", id);
            return null;
        }

        return convertToDTO(product);
    }

    @Override
    public ProductDTO getProductBySku(String sku) {
        log.info("根据SKU查询商品，SKU：{}", sku);

        if (!StringUtils.hasText(sku)) {
            log.warn("SKU为空");
            return null;
        }

        Product product = productMapper.selectProductBySku(sku);
        if (product == null) {
            log.warn("商品不存在，SKU：{}", sku);
            return null;
        }

        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getFeaturedProducts(Integer limit) {
        log.info("获取推荐商品，限制数量：{}", limit);

        List<Product> products = productMapper.selectFeaturedProducts(limit);
        return convertToDTOList(products);
    }

    @Override
    public List<ProductDTO> getNewProducts(Integer limit) {
        log.info("获取新品商品，限制数量：{}", limit);

        List<Product> products = productMapper.selectNewProducts(limit);
        return convertToDTOList(products);
    }

    @Override
    public List<ProductDTO> getHotProducts(Integer limit) {
        log.info("获取热销商品，限制数量：{}", limit);

        List<Product> products = productMapper.selectHotProducts(limit);
        return convertToDTOList(products);
    }

    @Override
    public List<ProductDTO> getRelatedProducts(Long categoryId, Long productId, Integer limit) {
        log.info("获取相关商品，分类ID：{}，商品ID：{}，限制数量：{}", categoryId, productId, limit);

        if (categoryId == null || productId == null) {
            log.warn("分类ID或商品ID为空");
            return Collections.emptyList();
        }

        List<Product> products = productMapper.selectRelatedProducts(categoryId, productId, limit);
        return convertToDTOList(products);
    }

    @Override
    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        log.info("批量获取商品，商品ID列表：{}", ids);

        if (CollectionUtils.isEmpty(ids)) {
            log.warn("商品ID列表为空");
            return Collections.emptyList();
        }

        List<Product> products = productMapper.selectProductsByIds(ids);
        return convertToDTOList(products);
    }

    @Override
    @Transactional
    public boolean updateProductStock(Long id, Integer stock) {
        log.info("更新商品库存，商品ID：{}，库存：{}", id, stock);

        if (id == null || stock == null || stock < 0) {
            log.warn("参数无效，商品ID：{}，库存：{}", id, stock);
            return false;
        }

        int result = productMapper.updateProductStock(id, stock);
        boolean success = result > 0;

        if (success) {
            log.info("商品库存更新成功，商品ID：{}，库存：{}", id, stock);
        } else {
            log.error("商品库存更新失败，商品ID：{}，库存：{}", id, stock);
        }

        return success;
    }

    @Override
    @Transactional
    public boolean increaseProductSales(Long id, Integer sales) {
        log.info("增加商品销量，商品ID：{}，销量：{}", id, sales);

        if (id == null || sales == null || sales <= 0) {
            log.warn("参数无效，商品ID：{}，销量：{}", id, sales);
            return false;
        }

        int result = productMapper.increaseProductSales(id, sales);
        boolean success = result > 0;

        if (success) {
            log.info("商品销量增加成功，商品ID：{}，销量：{}", id, sales);
        } else {
            log.error("商品销量增加失败，商品ID：{}，销量：{}", id, sales);
        }

        return success;
    }

    @Override
    public PageResult<ProductDTO> searchProducts(String keyword, Integer pageNum, Integer pageSize) {
        log.info("搜索商品，关键词：{}，页码：{}，每页大小：{}", keyword, pageNum, pageSize);

        ProductQueryDTO query = new ProductQueryDTO();
        query.setKeyword(keyword);
        if (pageNum != null && pageNum > 0) {
            query.setPageNum(pageNum);
        }
        if (pageSize != null && pageSize > 0) {
            query.setPageSize(pageSize);
        }

        return pageProducts(query);
    }

    @Override
    public boolean validateProductExists(Long id) {
        log.info("验证商品是否存在且有库存，商品ID：{}", id);

        if (id == null || id <= 0) {
            log.warn("商品ID无效：{}", id);
            return false;
        }

        Product product = productMapper.selectProductById(id);
        if (product == null) {
            log.warn("商品不存在，商品ID：{}", id);
            return false;
        }

        boolean exists = product.isOnSale() && product.hasStock();
        log.info("商品验证结果，商品ID：{}，存在且有库存：{}", id, exists);

        return exists;
    }

    /**
     * 将Product实体转换为ProductDTO
     *
     * @param product 商品实体
     * @return 商品DTO
     */
    private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);

        // 处理标签数组
        if (StringUtils.hasText(product.getTags())) {
            dto.setTags(product.getTags().split(","));
        }

        // 设置布尔类型字段
        dto.setIsFeatured(Integer.valueOf(1).equals(product.getIsFeatured()));
        dto.setIsNew(Integer.valueOf(1).equals(product.getIsNew()));
        dto.setIsHot(Integer.valueOf(1).equals(product.getIsHot()));

        return dto;
    }

    /**
     * 将Product实体列表转换为ProductDTO列表
     *
     * @param products 商品实体列表
     * @return 商品DTO列表
     */
    private List<ProductDTO> convertToDTOList(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}