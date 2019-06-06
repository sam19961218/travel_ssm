package com.bug.controll;


import com.bug.domain.ResultInfo;
import com.bug.domain.StaticConstant;
import com.bug.domain.User;
import com.bug.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户的控制类，负责用户注册、用户登录、用户退出，销毁当前用户Session域中数据、用户激活、以及查询当前用户是否登录
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
     * 用户注册
     * @param user
     * @param check 用户输入的验证码
     */
    @RequestMapping("/regist")
    @ResponseBody
    public ResultInfo registUser(User user, String check, HttpSession session){
        //验证校验
        //从sesion中获取验证码
        String checkcode_server = (String) session.getAttribute(StaticConstant.CHECKCODE_SERVER);
        session.removeAttribute(StaticConstant.CHECKCODE_SERVER);//为了保证验证码只能使用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            return info;
        }

        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        return info;
    }


    /**
     * 用户登录
     * http://localhost:8080/travel_ssm/loginServlet?username=thinkgem&password=fadsffadfsfdsf
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo loginUser(User user, HttpServletRequest request) {
        System.out.println(user);
        //3.调用Service查询
        User u = userService.login(user);
        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (u == null) {
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
        //5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }
        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);//登录成功标记
            //登录成功
            info.setFlag(true);
        }
        return info;
    }


    /**
     * 查询单个对象
     * 用于判断是否登录
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public User findUser(HttpServletRequest request) {
        //从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        //将user写回客户端
        return user;
    }


    /**
     * 用户退出 销毁Session 域中的数据
     */
    @RequestMapping("/exit")
    public void exitUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }


    /**
     * 用户激活
     */
    @RequestMapping(value = "/active")
    public void activeUser(String code, HttpServletResponse response) throws IOException {
        //1.获取激活码
        String msg = "请按照连接点击";
        if (code != null) {
            //2.调用service完成激活
            boolean flag = userService.active(code);
            //3.判断标记
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='../login.html'>登录</a>";
            } else {
                //激活失败
                msg = "这都激活失败，去死吧!";
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }


}
