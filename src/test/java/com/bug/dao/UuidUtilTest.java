package com.bug.dao;

import org.junit.Test;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 * 很难重复
 */
public final class UuidUtilTest {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 测试
     */
    @Test
    public void test() {
        System.out.println(UuidUtilTest.getUuid());
        System.out.println(UuidUtilTest.getUuid());
        System.out.println(UuidUtilTest.getUuid());
        System.out.println(UuidUtilTest.getUuid());
    }
}
