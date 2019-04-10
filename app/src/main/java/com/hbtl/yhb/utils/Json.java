package com.hbtl.yhb.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class Json {
    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJSONString(Object o) {
        try {
            return JSON.toJSONString(o);
        } catch (Exception e) {
            return null;
        }
    }
}
