package com.bug.service.impl;


import com.bug.dao.UserDao;
import com.bug.domain.User;
import com.bug.service.UserService;
import com.bug.util.MailUtils;
import com.bug.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 注册用户
     */
    @Override
    public boolean regist(User user) {
        //判断用户名和密码非空
        if (user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        //1. 根据用户查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断 u 是否为 null
        if (u != null) {
            //用户名存在注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");

        userDao.save(user);
        //3.激活邮件发送，邮件正文？
        String href = "href='http://localhost:8080/travel_ssm/user/active?code=" + user.getCode();
        String content = "<a " + href + "'>点击激活【Xxx旅游网】</a>";
        System.out.println(content);
        MailUtils.sendMail("979739448@qq.com", "请点击链接完成邮件注册：" + content + "", "激活邮件");
        return true;
    }

    /**
     * 激活用户
     *
     * @param code 激活码
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录方法
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }


}
