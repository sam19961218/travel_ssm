package com.bug.controll;

import com.bug.domain.Category;
import com.bug.service.CategoryService;
import com.bug.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 首页导航
 * 门票、酒店、香港车票、、、、等等
 *
 * 分类数据展示
 * http://localhost:8080/travel_ssm/category/findAll
 */

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 查询所有
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Category> findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用service查询所有
        return categoryService.findAll();
    }




}
