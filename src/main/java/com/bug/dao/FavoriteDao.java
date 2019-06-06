package com.bug.dao;


import com.bug.domain.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * tab_favorite
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid 景点id
     * @param uid 用户id
     */
    Favorite findByRidAndUid(@Param("rid")int rid, @Param("uid")int uid);

    /**
     * 根据rid 查询收藏次数
     *
     * @param rid  景点id
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid 景点id
     * @param uid 用户id
     */
    void add(@Param("rid")int rid, @Param("date") Date date, @Param("uid") int uid);
}
