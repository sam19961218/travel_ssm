package com.bug.service;

import java.util.Date;

public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid 景点id
     * @param uid 用户id
     */
     boolean isFavorite(int rid, int uid);


    /**
     * 添加收藏
     * @param rid 景点id
     * @param uid 用户id
     */
    void add(int rid, Date date, int uid);

}
