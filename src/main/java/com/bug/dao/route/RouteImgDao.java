package com.bug.dao.route;


import com.bug.domain.route.RouteImg;

import java.util.List;

/**
 * 每个景点的图片信息
 */
public interface RouteImgDao {
    /**
     * 根据route的id查询图片
     */
    List<RouteImg> findByRid(int rid);
}
