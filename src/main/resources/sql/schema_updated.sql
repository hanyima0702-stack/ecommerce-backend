-- =============================================
-- 电商平台数据库表结构（更新版）
-- 创建时间: 2024-01-01
-- 版本: 2.0.0
-- =============================================

-- 如果不存在数据库则创建
CREATE DATABASE IF NOT EXISTS ecommerce DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ecommerce;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密）',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `first_name` VARCHAR(50) COMMENT '名字',
    `last_name` VARCHAR(50) COMMENT '姓氏',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `birthday` DATE COMMENT '生日',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_phone` (`phone`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 商品分类表
CREATE TABLE IF NOT EXISTS `category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID，0表示顶级分类',
    `level` TINYINT DEFAULT 1 COMMENT '分类级别：1-一级，2-二级，3-三级',
    `sort_order` INT DEFAULT 0 COMMENT '排序值，越小越靠前',
    `icon` VARCHAR(255) COMMENT '分类图标',
    `image_url` VARCHAR(255) COMMENT '分类图片',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_level` (`level`),
    KEY `idx_status` (`status`),
    KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 3. 品牌表
CREATE TABLE IF NOT EXISTS `brand` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
    `name` VARCHAR(100) NOT NULL COMMENT '品牌名称',
    `logo` VARCHAR(255) COMMENT '品牌Logo',
    `description` TEXT COMMENT '品牌描述',
    `sort_order` INT DEFAULT 0 COMMENT '排序值',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `idx_status` (`status`),
    KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='品牌表';

-- 4. 商品表
CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `name` VARCHAR(255) NOT NULL COMMENT '商品名称',
    `description` TEXT COMMENT '商品描述',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `brand_id` BIGINT COMMENT '品牌ID',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    `original_price` DECIMAL(10,2) COMMENT '原价',
    `stock` INT DEFAULT 0 COMMENT '库存数量',
    `sales` INT DEFAULT 0 COMMENT '销量',
    `weight` DECIMAL(8,2) COMMENT '重量（kg）',
    `image_url` VARCHAR(255) COMMENT '主图URL',
    `images` TEXT COMMENT '商品图片列表（JSON格式）',
    `sku` VARCHAR(100) COMMENT '商品编码',
    `tags` VARCHAR(255) COMMENT '商品标签，逗号分隔',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `is_featured` TINYINT DEFAULT 0 COMMENT '是否推荐：0-否，1-是',
    `is_new` TINYINT DEFAULT 0 COMMENT '是否新品：0-否，1-是',
    `is_hot` TINYINT DEFAULT 0 COMMENT '是否热销：0-否，1-是',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sku` (`sku`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_brand_id` (`brand_id`),
    KEY `idx_price` (`price`),
    KEY `idx_status` (`status`),
    KEY `idx_sales` (`sales`),
    KEY `idx_created_time` (`created_time`),
    KEY `idx_is_featured` (`is_featured`),
    KEY `idx_is_new` (`is_new`),
    KEY `idx_is_hot` (`is_hot`),
    CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 5. 购物车表（新版）
CREATE TABLE IF NOT EXISTS `cart_new` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `item_count` INT DEFAULT 0 COMMENT '商品数量',
    `subtotal` DECIMAL(10,2) DEFAULT 0.00 COMMENT '商品小计',
    `shipping_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
    `discount_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    `total_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '总金额',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    CONSTRAINT `fk_cart_new_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表（新版）';

-- 6. 购物车商品表（新版）
CREATE TABLE IF NOT EXISTS `cart_item_new` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '购物车商品ID',
    `cart_id` BIGINT NOT NULL COMMENT '购物车ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `sku` VARCHAR(100) COMMENT '商品SKU',
    `product_name` VARCHAR(255) NOT NULL COMMENT '商品名称',
    `product_description` TEXT COMMENT '商品描述',
    `product_image_url` VARCHAR(255) COMMENT '商品图片URL',
    `product_price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    `product_original_price` DECIMAL(10,2) COMMENT '商品原价',
    `product_stock` INT DEFAULT 0 COMMENT '商品库存',
    `product_weight` DECIMAL(8,2) COMMENT '商品重量',
    `variant` JSON COMMENT '商品规格（颜色、尺寸等）',
    `quantity` INT NOT NULL DEFAULT 1 COMMENT '商品数量',
    `total_price` DECIMAL(10,2) NOT NULL COMMENT '商品总价（商品价格 × 数量）',
    `selected` TINYINT(1) DEFAULT 1 COMMENT '是否选择（用于结算）：0-未选中，1-选中',
    `product_status` TINYINT DEFAULT 1 COMMENT '商品状态：0-下架，1-上架',
    `remarks` VARCHAR(500) COMMENT '备注',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-删除，1-正常',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_cart_product_variant` (`cart_id`, `product_id`, `(CAST(`variant` AS CHAR(255)))`),
    KEY `idx_cart_id` (`cart_id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_sku` (`sku`),
    KEY `idx_selected` (`selected`),
    KEY `idx_status` (`status`),
    CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart_new` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车商品表（新版）';

-- 7. 订单表
CREATE TABLE IF NOT EXISTS `order_info` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no` VARCHAR(64) NOT NULL COMMENT '订单号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    `shipping_fee` DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
    `discount_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
    `actual_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `payment_method` TINYINT COMMENT '支付方式：1-支付宝，2-微信，3-银行卡',
    `payment_status` TINYINT DEFAULT 0 COMMENT '支付状态：0-未支付，1-已支付，2-已退款',
    `payment_time` DATETIME COMMENT '支付时间',
    `order_status` TINYINT DEFAULT 1 COMMENT '订单状态：1-待付款，2-待发货，3-待收货，4-已完成，5-已取消',
    `shipping_name` VARCHAR(50) COMMENT '收货人姓名',
    `shipping_phone` VARCHAR(20) COMMENT '收货人电话',
    `shipping_address` VARCHAR(500) COMMENT '收货地址',
    `shipping_province` VARCHAR(50) COMMENT '收货省份',
    `shipping_city` VARCHAR(50) COMMENT '收货城市',
    `shipping_district` VARCHAR(50) COMMENT '收货区县',
    `shipping_zip_code` VARCHAR(10) COMMENT '邮政编码',
    `shipping_time` DATETIME COMMENT '发货时间',
    `receive_time` DATETIME COMMENT '收货时间',
    `remark` VARCHAR(500) COMMENT '订单备注',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_status` (`order_status`),
    KEY `idx_payment_status` (`payment_status`),
    KEY `idx_created_time` (`created_time`),
    CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 8. 订单商品表
CREATE TABLE IF NOT EXISTS `order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `product_name` VARCHAR(255) NOT NULL COMMENT '商品名称',
    `product_image` VARCHAR(255) COMMENT '商品图片',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品单价',
    `quantity` INT NOT NULL COMMENT '购买数量',
    `total_price` DECIMAL(10,2) NOT NULL COMMENT '商品总价',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_product_id` (`product_id`),
    CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品表';

-- 9. 商品评价表
CREATE TABLE IF NOT EXISTS `product_review` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `rating` TINYINT NOT NULL COMMENT '评分：1-5星',
    `content` TEXT COMMENT '评价内容',
    `images` TEXT COMMENT '评价图片（JSON格式）',
    `is_anonymous` TINYINT DEFAULT 0 COMMENT '是否匿名：0-否，1-是',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-隐藏，1-显示',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_rating` (`rating`),
    KEY `idx_status` (`status`),
    CONSTRAINT `fk_review_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_review_order` FOREIGN KEY (`order_id`) REFERENCES `order_info` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品评价表';