package com.bug.dao;

import com.bug.domain.User;
import com.bug.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;


    @Test
    public void test05() {
        User user = userDao.findByUsernameAndPassword("thinkgem", "fadsffadfsfdsf");
        System.out.println(user);
    }

    @Test
    public void test() {
        System.out.println(userService.login(new User()));
    }

    @Test
    public void test02() {
        User user = userDao.findByUsername("hehe");
        System.out.println(user);
    }

    @Test
    public void test03() {
        System.out.println(userDao);
    }

    //private int uid;//用户id
//private String username;//用户名，账号
//private String password;//密码
//private String name;//真实姓名
//private String birthday;//出生日期
//private String sex;//男或女
//private String telephone;//手机号
//private String email;//邮箱
//private String status;//激活状态，Y代表激活，N代表未激活
//private String code;//激活码（要求唯一）
    @Test
    public void test04() {
        User user = new User();
        user.setUsername("张三9999");
        user.setPassword("123");
        user.setName("张三");
        user.setBirthday("2019-05-15");
        user.setSex("1");
        user.setTelephone("1778649163");
        user.setEmail("121@qq.com");
        user.setStatus("1");
        user.setCode("1");


        userDao.save(user);
        System.out.println(user);
    }

//    @Test
//    public void test04() throws IOException {
//        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
//        SqlSession openSession = sqlSessionFactory.openSession();
////
////        User user = openSession.selectOne("com.bug.dao.UserDa.findByUsername", "hehe");
////
////        System.out.println(user);
//
//    }
//
//    public SqlSessionFactory getSqlSessionFactory() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        return new SqlSessionFactoryBuilder().build(inputStream);
//    }
//
//
//    @Test
//    public void test05() throws IOException {
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//    }


}
