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

-- 5. 初始化商品评价数据
INSERT INTO `product_review` (`product_id`, `user_id`, `order_id`, `rating`, `content`, `is_anonymous`, `status`) VALUES
(1, 2, 1, 5, '非常棒的跑鞋！缓震效果很好，长距离跑步也不会累。', 0, 1),
(1, 2, 2, 4, '质量不错，但是价格有点贵。', 1, 1),
(2, 2, 3, 5, '日常训练的完美选择，非常舒适。', 0, 1),
(3, 2, 4, 5, '越野跑神器！抓地力超强，雨天也能跑。', 0, 1),
(4, 2, 5, 4, '轻便透气，适合速度训练。', 1, 1),
(8, 2, 6, 5, '拍照效果惊艳，系统流畅，值得购买！', 0, 1),
(9, 2, 7, 4, 'S Pen很好用，但是机身有点重。', 1, 1),
(12, 2, 8, 5, '降噪效果一流，音质出色，续航也不错。', 0, 1);