package com.bug.dao;


import com.bug.domain.User;
import org.apache.ibatis.annotations.Param;

// tab_user
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     */
    User findByUsername(String username);

    /**
     * 用户保存
     */
    void save(User user);

    /**
     * 根据激活码查找用户
     *
     * @param code 激活码
     */
    User findByCode(String code);

    /**
     * 修改用户的状态
     */
    void updateStatus(User user);

    /**
     * 通过用户名和密码查询用户
     * 多个参数要么使用@Param注解
     * 要么参数写 param1 param2 param3 ....
     */
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}

