package com.ecommerce.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 空分页结果
     */
    public static <T> PageResult<T> empty() {
        PageResult<T> result = new PageResult<>();
        result.setRecords(List.of());
        result.setTotal(0L);
        result.setCurrent(1L);
        result.setSize(10L);
        result.setPages(0L);
        result.setHasPrevious(false);
        result.setHasNext(false);
        return result;
    }

    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Long current, Long size) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(total);
        result.setCurrent(current);
        result.setSize(size);

        // 计算总页数
        long pages = (total + size - 1) / size;
        result.setPages(pages);

        // 计算是否有上一页和下一页
        result.setHasPrevious(current > 1);
        result.setHasNext(current < pages);

        return result;
    }

    /**
     * 判断是否有数据
     */
    public boolean hasRecords() {
        return records != null && !records.isEmpty();
    }

    /**
     * 获取当前页起始记录数
     */
    public long getStartRow() {
        if (current == null || size == null) return 0;
        return (current - 1) * size + 1;
    }

    /**
     * 获取当前页结束记录数
     */
    public long getEndRow() {
        if (current == null || size == null || total == null) return 0;
        long end = current * size;
        return Math.min(end, total);
    }
}