package com.bug.util;


import com.bug.domain.Category;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class JedisUtilTest {

    @Test
    public void test() {

        Jedis jedis = new Jedis("120.79.202.181", 6379);//如果使用空参构造器，默认值 "localhost", 6379 端口
        //2. 操作
        jedis.set("username", "zhangsan");
        System.out.println(jedis.get("username"));
        jedis.del("username");
        System.out.println(jedis.get("username"));
        //3. 关闭连接
        jedis.close();
    }


    @Test
    public void test01() {
        Jedis jedis = new Jedis("120.79.202.181");
        //获取所有的 键
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    @Test
    public void test02() {
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
//        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
////        for (Tuple tuple : categorys) {
////            System.out.println(tuple.getElement());
////            System.out.println((int) tuple.getScore());
////        }
    }


}
