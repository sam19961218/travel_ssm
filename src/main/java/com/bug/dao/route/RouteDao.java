package com.bug.dao.route;


import com.bug.domain.route.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// tab_route
public interface RouteDao {

    /**
     * 查询所有 cid 一样的记录条数
     * 如果rname有值就进性模糊查询，返回的结果是模糊查询的记录条数
     * 就是查询每一个分类表里面的记录条数
     */
    int findTotalCount(@Param("cid") int cid, @Param("rname") String rname);

    /**
     * 根据cid，start,pageSize查询当前页的数据集合
     * 和上面一样，加上一个模糊查询
     */
    List<Route> findByPage(@Param("cid") int cid, @Param("start") int start, @Param("pageSize") int pageSize, @Param("rname") String rname);



    /**
     * 根据cid查询
     */
     Route findOne(int cid);

}
