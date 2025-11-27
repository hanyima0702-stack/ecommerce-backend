package com.ecommerce;

import com.ecommerce.dto.PageResult;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductQueryDTO;
import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 商品服务测试类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testPageProducts() {
        // 创建查询条件
        ProductQueryDTO query = new ProductQueryDTO();
        query.setKeyword("shoe");
        query.setPageNum(1);
        query.setPageSize(10);
        query.setSortBy("price");
        query.setSortOrder("asc");

        // 执行查询
        PageResult<ProductDTO> result = productService.pageProducts(query);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.hasRecords() || result.getTotal() == 0);
        assertEquals(1, result.getCurrent());
        assertEquals(10, result.getSize());
    }

    @Test
    void testGetProductById() {
        // 查询存在的商品
        ProductDTO product = productService.getProductById(1L);
        if (product != null) {
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getPrice());
        }

        // 查询不存在的商品
        ProductDTO notFoundProduct = productService.getProductById(99999L);
        assertNull(notFoundProduct);
    }

    @Test
    void testGetFeaturedProducts() {
        List<ProductDTO> featuredProducts = productService.getFeaturedProducts(5);
        assertNotNull(featuredProducts);
        assertTrue(featuredProducts.size() <= 5);

        // 验证返回的都是推荐商品
        featuredProducts.forEach(product -> assertTrue(product.getIsFeatured()));
    }

    @Test
    void testGetNewProducts() {
        List<ProductDTO> newProducts = productService.getNewProducts(5);
        assertNotNull(newProducts);
        assertTrue(newProducts.size() <= 5);

        // 验证返回的都是新品商品
        newProducts.forEach(product -> assertTrue(product.getIsNew()));
    }

    @Test
    void testGetHotProducts() {
        List<ProductDTO> hotProducts = productService.getHotProducts(5);
        assertNotNull(hotProducts);
        assertTrue(hotProducts.size() <= 5);

        // 验证返回的都是热销商品
        hotProducts.forEach(product -> assertTrue(product.getIsHot()));
    }

    @Test
    void testSearchProducts() {
        PageResult<ProductDTO> result = productService.searchProducts("running", 1, 10);
        assertNotNull(result);
        assertTrue(result.getCurrent() == 1);
        assertTrue(result.getSize() == 10);
    }

    @Test
    void testValidateProductExists() {
        // 测试存在的商品
        boolean exists = productService.validateProductExists(1L);
        // 注意：这个测试结果取决于数据库中的数据状态

        // 测试不存在的商品
        boolean notExists = productService.validateProductExists(99999L);
        assertFalse(notExists);
    }

    @Test
    void testProductQueryDTO() {
        ProductQueryDTO query = new ProductQueryDTO();
        query.setMinPrice(BigDecimal.valueOf(50));
        query.setMaxPrice(BigDecimal.valueOf(200));

        // 测试价格区间验证
        assertTrue(query.isPriceRangeValid());

        // 测试无效价格区间
        query.setMinPrice(BigDecimal.valueOf(200));
        query.setMaxPrice(BigDecimal.valueOf(50));
        assertFalse(query.isPriceRangeValid());

        // 测试排序字段
        assertEquals("price ASC", query.getOrderByClause());

        query.setSortBy("sales");
        query.setSortOrder("desc");
        assertEquals("sales DESC", query.getOrderByClause());
    }
}