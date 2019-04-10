package com.hbtl.yhb.configs;

import android.text.TextUtils;

/**
 * Created by tiano on 2019/1/30.
 */
public class Config {
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_DATA = "extra_data";
    public static final int EXTRA_PAGESIZE = 20;
    public static final int BASE_PAGE = 1;
    public static String server_domain = "";

    public static String isLawfulPhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11 || !phone.startsWith("1")) {
            return "请输入有效的手机号";
        }
        return "";
    }

    public static String isLawfulName(String name) {
        if (TextUtils.isEmpty(name) || name.length() > 15) {
            return "请输入1-15位有效中文字符的姓名";
        }
        return "";
    }

    public static String isLawfulVerifyCode(String code) {
        if (TextUtils.isEmpty(code) || code.length() > 8 || code.length() < 4) {
            return "验证码错误";
        }
        return "";
    }

    public static String isLawfulIDCard(String card) {
        if (TextUtils.isEmpty(card) || card.length() > 18 || card.length() < 5) {
            return "请输入有效的身份证号码";
        }
        return "";
    }

    public static String isLawfulPwd(String pwd) {
        if (TextUtils.isEmpty(pwd) || pwd.length() > 20 || pwd.length() < 6) {
            return "请输入6~20位由数字和字母组成的密码";
        }
        return "";
    }

    public static String isLawfulCompany(String company) {
        if (TextUtils.isEmpty(company)) {
            return "请输入有效的单位名称";
        }
        return "";
    }

}
