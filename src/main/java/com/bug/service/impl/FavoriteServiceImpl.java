package com.bug.service.impl;

import com.bug.dao.FavoriteDao;
import com.bug.domain.Favorite;
import com.bug.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class FavoriteServiceImpl implements FavoriteService {


    @Autowired
    private FavoriteDao favoriteDao;

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }


    @Override
    public void add(int rid, Date date,  int uid) {
        favoriteDao.add(rid,date, uid);
    }
}
