package com.bug.service;


import com.bug.domain.Category;

import java.util.List;

/**
 * 首页导航
 * 门票、酒店、香港车票、、、、等等
 * <p>
 * 分类数据展示 处理
 */

public interface CategoryService {
    /**
     * 查询所有的分类
     */
    List<Category> findAll();
}
