package com.bug.service.impl;


import com.bug.dao.FavoriteDao;
import com.bug.dao.route.RouteDao;
import com.bug.dao.route.RouteImgDao;
import com.bug.dao.route.SellerDao;
import com.bug.domain.PageBean;
import com.bug.domain.route.Route;
import com.bug.domain.route.RouteImg;
import com.bug.domain.route.Seller;
import com.bug.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteDao routeDao;

    @Autowired
    RouteImgDao routeImgDao;

    @Autowired
    SellerDao sellerDao;

    @Autowired
    FavoriteDao favoriteDao;

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合      当前页面减一乘以每一天记录的条数
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        //除不尽需要多显示一页
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Route findOne(Integer rid) {
        //1.根据rid去route表中查询route对象
        Route route = routeDao.findOne(rid);

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //4. 查询收藏次数
        int count = favoriteDao.findCountByRid(rid);//查询这个景点收藏的次数
        route.setCount(count);
        return route;
    }



}

