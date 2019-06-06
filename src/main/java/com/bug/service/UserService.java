package com.bug.service;


import com.bug.domain.User;

public interface UserService {
    /**
     * 注册用户
     */
    boolean regist(User user);
    
    boolean active(String code);

    User login(User user);
}
