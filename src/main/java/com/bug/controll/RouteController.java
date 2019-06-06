package com.bug.controll;

import com.bug.domain.PageBean;
import com.bug.domain.User;
import com.bug.domain.route.Route;
import com.bug.service.FavoriteService;
import com.bug.service.RouteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 查询cid为5的，查询20条记录，重第5页开始查询
 * http://localhost:8080/travel_ssm/route/pageQuery?currentPage=5&pageSize=20&cid=5
 * 默认当前页面为1、每页显示5条、
 * http://localhost:8080/travel_ssm/route/pageQuery?cid=5
 * <p>
 * <p>
 * http://localhost:8080/travel_ssm/route/pageQuery?cid=5&name=西安
 * <p>
 * http://localhost:8080/travel_ssm/route/pageQuery?cid=5
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    RouteService routeService;
    @Autowired
    FavoriteService favoriteService;

    /**
     * 分页查询
     * 如果有 rname 这个时候我们就需要模糊查询
     *
     * @param currentPage 当前页数
     * @param pageSize    查询多少页
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(@RequestParam(defaultValue = "-1") int currentPage,
                              @RequestParam(defaultValue = "-1") int pageSize,
                              @RequestParam(name = "cid") String cidStr,
                              String rname
    ) {
        //1.接受参数

        //接受rname 线路名称
        if (rname == null || rname.equals("null")) {//没有给他一个空字符串
            rname = null;
        }

        int cid = 0;//类别id
        //2.处理参数
        //在首页 搜索 cid = "null" 会发生类型转换异常
        //http://localhost:8080/travel_ssm/route_list.html?cid=null&rname=西安
        if (cidStr != null && cidStr.length() > 0 && !cidStr.equals("null")) {
            cid = Integer.parseInt(cidStr);
        }

        if (currentPage == -1) {//当前页码，如果不传递，则默认为第一页
            currentPage = 1;
        }

        if (pageSize == -1) {//每页显示条数，如果不传递，默认每页显示5条记录
            pageSize = 5;
        }
        //3. 调用service查询PageBean对象
        //4. 将pageBean对象序列化为json，返回

        return routeService.pageQuery(cid, currentPage, pageSize, rname);
    }


    /**
     * 根据id查询一个旅游线路的详细信息
     * <p>
     * http://localhost:8080/travel_ssm/route/findOne?rid=1
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public Route findOne(Integer rid) {
        //1.接收id
        //2.调用service查询route对象
        //3.转为json写回客户端
        return routeService.findOne(rid);
    }


    /**
     * http://localhost:8080/travel_ssm/route/isFavorite?rid=1
     */
    @RequestMapping("/isFavorite")
    @ResponseBody
    public Boolean isFavorite(int rid, HttpSession session) {
        //1. 获取线路id
        //2. 获取当前登录的用户 user
        User user = (User) session.getAttribute("user");

        int uid = -1;//用户id  标记 用户尚未登录
        if (user != null) {
            //用户已经登录
            uid = user.getUid();
        }
        //3. 调用FavoriteService查询是否收藏
        //return true;
        return favoriteService.isFavorite(rid, uid);
    }


    /**
     * 添加收藏
     * http://localhost:8080/travel_ssm/route/addFavorite?rid=5
     */
    @RequestMapping("/addFavorite")
    @ResponseBody
    public void addFavorite(int rid, HttpSession session) {
        //1. 获取线路rid
        //2. 获取当前登录的用户
        User user = (User) session.getAttribute("user");
        int uid;//用户id
        if (user == null) {
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3. 调用service添加
        Date date = new Date();
        favoriteService.add(rid, date, uid);
    }


}



