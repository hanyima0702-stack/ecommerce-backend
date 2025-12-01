-- =============================================
-- 电商平台初始化数据
-- 创建时间: 2024-01-01
-- 版本: 1.0.0
-- =============================================

USE ecommerce;

-- 1. 初始化用户数据
INSERT INTO `user` (`username`, `password`, `email`, `nickname`, `gender`, `status`) VALUES
('admin', '$2a$10$7JB720yubVSOfvVHdQh6CO3zsjGK6m8PKdEdTWbAPMtvnKI9gEZOG', 'admin@ecommerce.com', '管理员', 1, 1),
('testuser', '$2a$10$7JB720yubVSOfvVHdQh6CO3zsjGK6m8PKdEdTWbAPMtvnKI9gEZOG', 'test@ecommerce.com', '测试用户', 1, 1);
-- 密码都是: password123

-- 2. 初始化分类数据
INSERT INTO `category` (`name`, `parent_id`, `level`, `sort_order`, `status`) VALUES
('服装鞋帽', 0, 1, 1, 1),
('运动鞋', 1, 2, 1, 1),
('休闲鞋', 1, 2, 2, 1),
('靴子', 1, 2, 3, 1),
('电子产品', 0, 1, 2, 1),
('手机数码', 5, 2, 1, 1),
('电脑办公', 5, 2, 2, 1),
('家用电器', 0, 1, 3, 1),
('厨房电器', 8, 2, 1, 1),
('生活电器', 8, 2, 2, 1);

-- 3. 初始化品牌数据
INSERT INTO `brand` (`name`, `description`, `sort_order`, `status`) VALUES
('Nike', '全球著名运动品牌', 1, 1),
('Adidas', '德国运动品牌', 2, 1),
('Apple', '美国科技公司', 1, 1),
('Samsung', '韩国电子集团', 2, 1),
('Xiaomi', '中国科技公司', 3, 1),
('Sony', '日本电子公司', 4, 1);

-- 4. 初始化商品数据
INSERT INTO `product` (`name`, `description`, `category_id`, `brand_id`, `price`, `original_price`, `stock`, `sales`, `image_url`, `sku`, `tags`, `status`, `is_featured`, `is_new`, `is_hot`) VALUES
-- 运动鞋
('Air Zoom Pegasus', 'High performance running shoe with responsive cushioning', 2, 1, 120.00, 150.00, 100, 120, '/images/products/nike-pegasus.jpg', 'NZ-PEGASUS-001', 'running,performance,cushioning', 1, 1, 0, 1),
('Cloud Runner 5', 'Comfortable running shoe perfect for daily training', 2, 2, 95.50, 120.00, 80, 98, '/images/products/adidas-cloud.jpg', 'AD-CLOUD-005', 'running,comfort,daily', 1, 0, 0, 0),
('TrailBlazer XT', 'Durable trail running shoe for off-road adventures', 2, 1, 150.00, 180.00, 60, 210, '/images/products/nike-trail.jpg', 'NZ-TRAIL-XT', 'trail,running,durable', 1, 1, 1, 1),
('Swift Pace Trainer', 'Lightweight and responsive training shoe', 2, 2, 110.00, 135.00, 75, 154, '/images/products/adidas-swift.jpg', 'AD-SWIFT-001', 'training,lightweight,responsive', 1, 0, 1, 0),
('Classic Slip-On', 'Iconic casual footwear for everyday comfort', 3, 1, 65.00, 80.00, 200, 500, '/images/products/nike-slipon.jpg', 'NZ-SLIP-001', 'casual,classic,comfort', 1, 0, 0, 1),
('Urban Minimalist', 'Sleek and versatile design for modern lifestyle', 3, 2, 135.00, 160.00, 40, 32, '/images/products/adidas-urban.jpg', 'AD-URBAN-001', 'casual,modern,versatile', 1, 1, 1, 0),

-- 电子产品
('iPhone 15 Pro', 'Latest flagship smartphone with advanced camera system', 6, 3, 999.00, 1099.00, 50, 1500, '/images/products/iphone15.jpg', 'AP-IP15P-128', 'smartphone,flagship,camera', 1, 1, 1, 1),
('Galaxy S24 Ultra', 'Premium Android smartphone with S Pen', 6, 4, 1199.00, 1299.00, 40, 800, '/images/products/galaxy-s24.jpg', 'SS-GS24U-256', 'smartphone,android,premium', 1, 1, 1, 0),
('MacBook Air M2', 'Ultra-thin laptop with powerful M2 chip', 7, 3, 1299.00, 1499.00, 30, 600, '/images/products/macbook-air.jpg', 'AP-MBA-M2-256', 'laptop,apple,portable', 1, 1, 0, 1),
('Xiaomi 14 Pro', 'Flagship Android phone with Leica camera', 6, 5, 699.00, 799.00, 80, 1200, '/images/products/xiaomi-14.jpg', 'XM-14P-256', 'smartphone,android,leica', 1, 0, 1, 1),
('Sony WH-1000XM5', 'Premium noise-cancelling wireless headphones', 6, 6, 399.00, 450.00, 60, 400, '/images/products/sony-headphones.jpg', 'SN-WH1000XM5', 'headphones,wireless,noise-cancelling', 1, 1, 0, 1),

-- 家用电器
('RoboVac G30', 'Intelligent robot vacuum cleaner with mapping', 9, 4, 299.00, 350.00, 35, 250, '/images/products/robot-vacuum.jpg', 'SS-ROBO-G30', 'vacuum,robot,smart', 1, 1, 1, 0),
('Air Purifier Pro', 'HEPA air purifier for large rooms', 10, 6, 199.00, 250.00, 45, 180, '/images/products/air-purifier.jpg', 'SN-AIR-PRO', 'air,purifier,hepa', 1, 0, 0, 0);

-- 5. 初始化订单数据
INSERT INTO `order_info` (`order_no`, `user_id`, `total_amount`, `shipping_fee`, `discount_amount`, `actual_amount`, `payment_method`, `payment_status`, `payment_time`, `order_status`, `shipping_name`, `shipping_phone`, `shipping_address`, `shipping_province`, `shipping_city`, `shipping_district`, `shipping_zip_code`, `shipping_time`, `receive_time`, `remark`) VALUES
-- 已完成订单 (购买 iPhone 15 Pro)
('20231026001', 2, 999.00, 30.00, 0.00, 1029.00, 1, 1, '2023-10-26 14:30:00', 4, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', '2023-10-27 10:00:00', '2023-10-28 16:30:00', '请快递到门'),

-- 已发货订单 (购买 TrailBlazer XT)
('20231024005', 2, 150.00, 15.00, 20.00, 145.00, 2, 1, '2023-10-24 11:20:00', 3, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', '2023-10-25 09:15:00', NULL, '包装要牢固'),

-- 待付款订单 (购买 Sony WH-1000XM5)
('20231023011', 2, 399.00, 0.00, 0.00, 399.00, NULL, 0, NULL, 1, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', NULL, NULL, '需要发票'),

-- 已取消订单 (购买多款运动鞋)
('20231022018', 2, 275.50, 20.00, 50.00, 245.50, NULL, 0, NULL, 5, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', NULL, NULL, '用户主动取消'),

-- 其他已完成订单
('20231020003', 2, 175.00, 10.00, 0.00, 185.00, 1, 1, '2023-10-20 16:45:00', 4, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', '2023-10-21 14:00:00', '2023-10-22 10:20:00', NULL),

('20231018007', 2, 1299.00, 25.00, 100.00, 1224.00, 3, 1, '2023-10-18 09:30:00', 4, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', '2023-10-19 11:00:00', '2023-10-20 15:45:00', '商品质量很好'),

('20231015012', 2, 395.00, 15.00, 0.00, 410.00, 2, 1, '2023-10-15 20:15:00', 4, '张三', '13800138001', '北京市朝阳区建国路88号SOHO现代城A座2108室', '北京市', '北京市', '朝阳区', '100022', '2023-10-16 13:30:00', '2023-10-17 09:10:00', NULL);

-- 6. 初始化购物车数据
INSERT INTO `cart` (`user_id`, `product_id`, `quantity`, `selected`) VALUES
-- 测试用户 (user_id = 2) 的购物车商品
(2, 1, 1, 1),  -- Air Zoom Pegasus，选中
(2, 2, 1, 1),  -- Cloud Runner 5，选中
(2, 3, 1, 0),  -- TrailBlazer XT，未选中
(2, 5, 1, 1),  -- Classic Slip-On，选中
(2, 6, 1, 0),  -- Urban Minimalist，未选中
(2, 8, 1, 1),  -- iPhone 15 Pro，选中
(2, 12, 1, 1); -- Sony WH-1000XM5，选中

-- 7. 初始化订单商品数据
INSERT INTO `order_item` (`order_id`, `product_id`, `product_name`, `product_image`, `price`, `quantity`, `total_price`) VALUES
-- 订单 20231026001 的商品 (iPhone 15 Pro)
(1, 8, 'iPhone 15 Pro', '/images/products/iphone15.jpg', 999.00, 1, 999.00),

-- 订单 20231024005 的商品 (TrailBlazer XT)
(2, 3, 'TrailBlazer XT', '/images/products/nike-trail.jpg', 150.00, 1, 150.00),

-- 订单 20231023011 的商品 (Sony WH-1000XM5)
(3, 12, 'Sony WH-1000XM5', '/images/products/sony-headphones.jpg', 399.00, 1, 399.00),

-- 订单 20231022018 的商品 (多个运动鞋)
(4, 1, 'Air Zoom Pegasus', '/images/products/nike-pegasus.jpg', 120.00, 2, 240.00),
(4, 2, 'Cloud Runner 5', '/images/products/adidas-cloud.jpg', 95.50, 1, 95.50),

-- 订单 20231020003 的商品 (多个商品)
(5, 4, 'Swift Pace Trainer', '/images/products/adidas-swift.jpg', 110.00, 1, 110.00),
(5, 5, 'Classic Slip-On', '/images/products/nike-slipon.jpg', 65.00, 1, 65.00),

-- 订单 20231018007 的商品 (iPhone 15 Pro - 使用折扣价)
(6, 8, 'iPhone 15 Pro', '/images/products/iphone15.jpg', 1299.00, 1, 1299.00),

-- 订单 20231015012 的商品 (多个商品)
(7, 3, 'TrailBlazer XT', '/images/products/nike-trail.jpg', 150.00, 1, 150.00),
(7, 4, 'Swift Pace Trainer', '/images/products/adidas-swift.jpg', 110.00, 1, 110.00),
(7, 12, 'Sony WH-1000XM5', '/images/products/sony-headphones.jpg', 399.00, 1, 399.00);

-- 8. 初始化商品评价数据
INSERT INTO `product_review` (`product_id`, `user_id`, `order_id`, `rating`, `content`, `images`, `is_anonymous`, `status`) VALUES
-- 对应订单中的商品评价
(1, 2, 4, 5, '非常棒的跑鞋！缓震效果很好，长距离跑步也不会累。', '["/images/reviews/pegasus-1.jpg", "/images/reviews/pegasus-2.jpg"]', 0, 1),
(2, 2, 4, 4, '质量不错，但是价格有点贵。', NULL, 1, 1),
(4, 2, 5, 5, '日常训练的完美选择，非常舒适。', NULL, 0, 1),
(5, 2, 5, 5, '经典款，百搭又舒适，推荐购买！', NULL, 1, 1),
(8, 2, 1, 5, '拍照效果惊艳，系统流畅，值得购买！', '["/images/reviews/iphone-1.jpg", "/images/reviews/iphone-2.jpg"]', 0, 1),
(3, 2, 2, 5, '越野跑神器！抓地力超强，雨天也能跑。', '["/images/reviews/trail-1.jpg"]', 0, 1),
(4, 2, 7, 4, '轻便透气，适合速度训练。', NULL, 1, 1),
(12, 2, 7, 5, '降噪效果一流，音质出色，续航也不错。', NULL, 0, 1);