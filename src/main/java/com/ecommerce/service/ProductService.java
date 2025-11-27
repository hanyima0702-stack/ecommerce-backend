package com.ecommerce.service;

import com.ecommerce.dto.PageResult;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductQueryDTO;
import com.github.pagehelper.PageInfo;

/**
 * 商品服务接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * 分页查询商品列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<ProductDTO> pageProducts(ProductQueryDTO query);

    /**
     * 根据ID查询商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    ProductDTO getProductById(Long id);

    /**
     * 根据SKU查询商品
     *
     * @param sku 商品SKU
     * @return 商品信息
     */
    ProductDTO getProductBySku(String sku);

    /**
     * 获取推荐商品
     *
     * @param limit 限制数量
     * @return 推荐商品列表
     */
    java.util.List<ProductDTO> getFeaturedProducts(Integer limit);

    /**
     * 获取新品商品
     *
     * @param limit 限制数量
     * @return 新品商品列表
     */
    java.util.List<ProductDTO> getNewProducts(Integer limit);

    /**
     * 获取热销商品
     *
     * @param limit 限制数量
     * @return 热销商品列表
     */
    java.util.List<ProductDTO> getHotProducts(Integer limit);

    /**
     * 获取相关商品
     *
     * @param categoryId 分类ID
     * @param productId  当前商品ID
     * @param limit      限制数量
     * @return 相关商品列表
     */
    java.util.List<ProductDTO> getRelatedProducts(Long categoryId, Long productId, Integer limit);

    /**
     * 批量获取商品
     *
     * @param ids 商品ID列表
     * @return 商品列表
     */
    java.util.List<ProductDTO> getProductsByIds(java.util.List<Long> ids);

    /**
     * 更新商品库存
     *
     * @param id    商品ID
     * @param stock 库存数量
     * @return 是否成功
     */
    boolean updateProductStock(Long id, Integer stock);

    /**
     * 增加商品销量
     *
     * @param id    商品ID
     * @param sales 销量
     * @return 是否成功
     */
    boolean increaseProductSales(Long id, Integer sales);

    /**
     * 搜索商品
     *
     * @param keyword 关键词
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<ProductDTO> searchProducts(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 验证商品是否存在且有库存
     *
     * @param id 商品ID
     * @return 是否存在且有库存
     */
    boolean validateProductExists(Long id);
}