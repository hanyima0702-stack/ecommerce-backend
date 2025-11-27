package com.ecommerce.mapper;

import com.ecommerce.dto.ProductQueryDTO;
import com.ecommerce.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper接口
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据条件查询商品列表
     *
     * @param query 查询条件
     * @return 商品列表
     */
    List<Product> selectProductList(@Param("query") ProductQueryDTO query);

    /**
     * 根据ID查询商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    Product selectProductById(@Param("id") Long id);

    /**
     * 查询商品总数
     *
     * @param query 查询条件
     * @return 商品总数
     */
    Long selectProductCount(@Param("query") ProductQueryDTO query);

    /**
     * 根据SKU查询商品
     *
     * @param sku 商品SKU
     * @return 商品信息
     */
    Product selectProductBySku(@Param("sku") String sku);

    /**
     * 查询推荐商品
     *
     * @param limit 限制数量
     * @return 推荐商品列表
     */
    List<Product> selectFeaturedProducts(@Param("limit") Integer limit);

    /**
     * 查询新品商品
     *
     * @param limit 限制数量
     * @return 新品商品列表
     */
    List<Product> selectNewProducts(@Param("limit") Integer limit);

    /**
     * 查询热销商品
     *
     * @param limit 限制数量
     * @return 热销商品列表
     */
    List<Product> selectHotProducts(@Param("limit") Integer limit);

    /**
     * 查询相关商品（同分类）
     *
     * @param categoryId 分类ID
     * @param productId  当前商品ID（排除）
     * @param limit      限制数量
     * @return 相关商品列表
     */
    List<Product> selectRelatedProducts(@Param("categoryId") Long categoryId,
                                       @Param("productId") Long productId,
                                       @Param("limit") Integer limit);

    /**
     * 更新商品库存
     *
     * @param id     商品ID
     * @param stock  库存数量
     * @return 影响行数
     */
    int updateProductStock(@Param("id") Long id, @Param("stock") Integer stock);

    /**
     * 增加商品销量
     *
     * @param id    商品ID
     * @param sales 销量
     * @return 影响行数
     */
    int increaseProductSales(@Param("id") Long id, @Param("sales") Integer sales);

    /**
     * 批量查询商品
     *
     * @param ids 商品ID列表
     * @return 商品列表
     */
    List<Product> selectProductsByIds(@Param("ids") List<Long> ids);
}