package com.bug.service;


import com.bug.domain.PageBean;
import com.bug.domain.route.Route;

/**
 * 线路Service
 */
public interface RouteService {

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询 一条具体旅游信息
     */
    Route findOne(Integer rid);
}
