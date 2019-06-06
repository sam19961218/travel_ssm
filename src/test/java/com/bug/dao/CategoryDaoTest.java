package com.bug.dao;

import com.bug.domain.Category;
import com.bug.service.impl.CategoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CategoryDaoTest {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryServiceImpl categoryService;


    @Test
    public void test() {
        List<Category> categories = categoryDao.findAll();
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    /**
     * 门票
     * 酒店
     * 香港车票
     * 处境游
     * 国内游
     * 港澳游
     * 抱团定制
     * 全球自由行
     */
    @Test
    public void test02() {
        Jedis jedis = new Jedis("120.79.202.181");
        Set<String> categorys = jedis.zrange("category", 0, -1);
        categorys.forEach(System.out::println);
    }


    @Test
    public void test03() {
        Jedis jedis = new Jedis("120.79.202.181");
        Set<String> keys = jedis.keys("*");
        //删除所有的键
        for (String key : keys) {
            jedis.del(key);
        }
        System.out.println(jedis.keys("*"));
    }
    
    
    @Test
    public void test04() {
        Jedis jedis = new Jedis("120.79.202.181");
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        categorys.forEach(System.out::println);
    }

    @Test
    public void test05() {
        List<Category> categories = categoryService.findAll();
        categories.forEach(System.out::println);
    }
}
