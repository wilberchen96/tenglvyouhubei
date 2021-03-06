package com.hbtl.yhb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hbtl.yhb.application.BaseApplication;


public class SpManager {
    //默认文件名
    public static final String COMMON_FILE_NAME = "common_file";


    public static final String KEY_ACCOUNT = "account";


    public static void saveData(String key, Object data) {
        saveData(COMMON_FILE_NAME, key, data);
    }

    public static Object getData(String key, Object defValue) {
        return getData(COMMON_FILE_NAME, key, defValue);
    }

    public static void saveData(String filename, String key, Object data) {
        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public static Object getData(String filename, String key, Object defValue) {
        String type = defValue.getClass().getSimpleName();

        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);
        //defValue为为默认值，如果当前获取不到数据就返回它 
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }
        return null;
    }


    public static void clear(String filename) {
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static void clearByKey(String key) {
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }
    public static void clearByKeyNow(String key) {
        SharedPreferences sharedPreferences = BaseApplication.getInstance().getSharedPreferences(COMMON_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }
}
