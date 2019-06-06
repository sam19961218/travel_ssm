package com.bug.dao.route;


import com.bug.domain.route.Seller;
/**
 * 操作商家的 dao
 */
public interface SellerDao {
    /**
     * 根据id查询
     */
    Seller findById(int id);
}
