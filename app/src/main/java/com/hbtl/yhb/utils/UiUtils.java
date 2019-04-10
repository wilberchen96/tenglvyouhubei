package com.hbtl.yhb.utils;

import android.widget.Toast;

import com.hbtl.yhb.application.BaseApplication;

public class UiUtils {
    public static void showToast(String msg) {
        Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
