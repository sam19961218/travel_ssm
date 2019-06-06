package com.bug.dao;

import com.bug.dao.route.RouteDao;
import com.bug.dao.route.RouteImgDao;
import com.bug.dao.route.SellerDao;
import com.bug.domain.route.Route;
import com.bug.domain.route.RouteImg;
import com.bug.domain.route.Seller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class RouteDaoTest {

    @Autowired
    RouteDao routeDao;

    @Autowired
    RouteImgDao routeImgDao;

    @Autowired
    SellerDao sellerDao;
    
    
    @Test
    public void test() {
        int totalCount = routeDao.findTotalCount(5,"西安");
        System.out.println(totalCount);
    }


    @Test
    public void test03() {
           System.out.println("".length());
    }

    /**
     * 查询 cid 为5 ，重第4页开始
     */
    @Test
    public void test02() {
        List<Route> routeList = routeDao.findByPage(5, 4, 10,"西安");
        int i = 1;
        for (Route route : routeList) {
            System.out.println("------------------"+(i++)+"--------------");
            System.out.println(route);
            System.out.println();
        }

    }



    @Test
    public void test04() {
        Route route = routeDao.findOne(2);
        System.out.println(route);

        System.out.println("-------------"+route.getRid()+"------------");
        routeImgDao.findByRid(route.getRid());

        System.out.println("---------"+route.getSid()+"----------------");
        sellerDao.findById(route.getSid());
    }

    @Test
    public void test05() {
        System.out.println("\n------------------------\n");
        List<RouteImg> routeImgs = routeImgDao.findByRid(1);
        routeImgs.forEach(System.out::println);
        System.out.println("\n------------------------\n");
    }

    @Test
    public void test06() {
        System.out.println("\n------------------------\n");
        Seller byId = sellerDao.findById(1);
        System.out.println(byId);

        System.out.println("\n------------------------\n");
    }
    
}
