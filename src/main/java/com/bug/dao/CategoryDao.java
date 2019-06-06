package com.bug.dao;


import com.bug.domain.Category;

import java.util.List;

//tab_category
// 首页、门票、商店、、
public interface CategoryDao {
    /**
     * 查询所有
     */
    List<Category> findAll();


}
