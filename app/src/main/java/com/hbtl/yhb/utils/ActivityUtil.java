package com.hbtl.yhb.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.ClipboardManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.hbtl.yhb.application.BaseApplication;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityUtil {


    /**
     * 将格林时间的毫秒转化成字符串
     */
    public static String convert(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 将格林时间的毫秒转化成字符串
     */
    public static String convertM(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static String convertT(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static String convertY(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    //比较两个日期相差的天数
    public static int daysOfTwo(long fDate, long oDate) {

        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();
        aCalendar.setTimeInMillis(fDate);
        bCalendar.setTimeInMillis(oDate);
        int days = 0;
        while (aCalendar.before(bCalendar)) {
            days++;
            aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days + 1;
    }


    /***
     * 将格林时间的毫秒转化成字符串
     *
     * @param
     * @return
     */
    public static String convertD(long mill) {
        Date date = new Date(mill);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /***
     * 将毫秒转换成时，和分
     *
     * @param mill
     * @return
     */
    public static String convertMS(long mill) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String ms = formatter.format(mill);
        return ms;
    }

    /***
     * 将年，月，日，时，分转换成毫秒
     *
     * @param
     * @return
     */
    public static long convertYMDHM(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date ymdhm = formatter.parse(s);
            return ymdhm.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /***
     * 将年，月，日转换成毫秒
     *
     * @param
     * @return
     */
    public static long convertYMD(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date ymdhm = formatter.parse(s);
            return ymdhm.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /***
     * 将毫秒转换成时,分,秒
     *
     * @param
     * @return
     */
    public static String convertHMS(long l) {
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        String mill = conversion(hour) + ":" + conversion(min) + ":" + conversion(s);
        return mill;
    }


    public static String conversion(long mill) {
        if (mill < 10)
            return "0" + mill;
        if (mill > 99)
            return "99";
        return String.valueOf(mill);
    }

    /**
     * 阿拉伯数字转中文数字
     *
     * @param amountPart
     * @return
     */
    public static String partTranslate(int amountPart) {
        String[] chineseDigits = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

        if (amountPart < 0 || amountPart > 10000) {
            return "";
        }

        String[] units = new String[]{"", "十", "百", "千"};

        int temp = amountPart;

        String amountStr = String.valueOf(amountPart);
        int amountStrLength = amountStr.length();
        boolean lastIsZero = true; //在从低位往高位循环时，记录上一位数字是不是 0
        String chineseStr = "";

        for (int i = 0; i < amountStrLength; i++) {
            if (temp == 0)  // 高位已无数据
                break;
            int digit = temp % 10;
            if (digit == 0) { // 取到的数字为 0
                if (!lastIsZero)  //前一个数字不是 0，则在当前汉字串前加“零”字;
                    chineseStr = "零" + chineseStr;
                lastIsZero = true;
            } else { // 取到的数字不是 0
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }


    /**
     * 获取当前系统时间年
     */

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;

    }

    /***
     * 获取当前系统时间月
     */

    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }


    public static int getMonth(long mill) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(mill);
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }


    public static int getDay(long mill) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(mill);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取当前系统时间日
     */

    public static int getDay() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取当前系统时间 时
     */
    public static int getHour() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取当前系统时间 分
     */
    public static int getMinute() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 截取字符串
     *
     * @param data   传入的字符串
     * @param length 要截取的长度
     * @return
     */
    public static String subValue(String data, int length) {
        if (data.length() >= length) {
            return data.substring(0, length) + "...";
        }
        return data;
    }

    /**
     * 检测data是否是合法的,暂时添加weixin.yilule.com后期指定一个标准，客户端不需要多次添加判断
     *
     * @param data
     * @return
     */
    public static boolean isKeyValid(String data) {
        if (TextUtils.isEmpty(data)) {
            return false;
        }
        if (data.matches("^http://(?:test.)?webapi\\.yilule\\.com\\:5580/api/TourData\\?id=[0-9]+$")) {
            return true;
        } else if (data.matches("^http://weixin\\.yilule\\.com/newNeirong.htm\\?id=[0-9]+$")) {
            return true;
        }
        return false;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取当前系统SDK版本号
     */
    public static int getSystemVersion() {
        /*获取当前系统的android版本号*/
        int version = Build.VERSION.SDK_INT;
        return version;
    }

    /**
     * 查看网络是否连接
     *
     * @param
     * @return
     */
    public static boolean isNetConnected() {
        boolean isNetConnected;
        // 获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
//			String name = info.getTypeName();
//			L.i("当前网络名称：" + name);
            isNetConnected = true;
        } else {
            isNetConnected = false;
        }
        return isNetConnected;
    }

    /**
     * 屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 在onCreate()获得view的高度
     *
     * @param view 控件
     * @return 高度
     */
    public static int getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 在onCreate()获得view的宽度
     *
     * @param view 控件
     * @return 宽度
     */
    public static int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    public static void setCompoundDrawables(RadioButton radioButton, int resId, int gravity) {
        if (resId != 0) {
            Drawable drawable = BaseApplication.getInstance().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            switch (gravity) {
                case Gravity.TOP:
                    radioButton.setCompoundDrawables(null, drawable, null, null);
                    break;
                case Gravity.BOTTOM:
                    radioButton.setCompoundDrawables(null, null, null, drawable);
                    break;
                case Gravity.LEFT:
                    radioButton.setCompoundDrawables(drawable, null, null, null);
                    break;
                case Gravity.RIGHT:
                    radioButton.setCompoundDrawables(null, null, drawable, null);
                    break;
                default:
                    radioButton.setCompoundDrawables(null, null, null, null);
                    break;
            }
        } else {
            radioButton.setCompoundDrawables(null, null, null, null);
        }
    }

    /**
     * 设置图片在TextView 的方向
     *
     * @param tx
     * @param resId
     */
    public static void setCompoundDrawables(TextView tx, int resId, int gravity) {
        if (resId != 0) {
            Drawable drawable = BaseApplication.getInstance().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            switch (gravity) {
                case Gravity.TOP:
                    tx.setCompoundDrawables(null, drawable, null, null);
                    break;
                case Gravity.BOTTOM:
                    tx.setCompoundDrawables(null, null, null, drawable);
                    break;
                case Gravity.LEFT:
                    tx.setCompoundDrawables(drawable, null, null, null);
                    break;
                case Gravity.RIGHT:
                    tx.setCompoundDrawables(null, null, drawable, null);
                    break;
                default:
                    tx.setCompoundDrawables(null, null, null, null);
                    break;
            }
        } else {
            tx.setCompoundDrawables(null, null, null, null);
        }
    }


    /**
     * 进入设置GPS界面
     */

    public static void gotoSettingGps(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            mContext.startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 计算文字的宽度
     */

    public static int getTextWidth(String name) {
        Paint pFont = new Paint();
        Rect rect = new Rect();
        pFont.getTextBounds(name, 0, name.length(), rect);
        return rect.width();
    }

    /**
     * 关键字高亮变色
     *
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return
     */
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    public static String getWeekOfDate(long dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    //判断是否安装了应用
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 验证身份证号码
     *
     * @param IDStr 身份证号
     * @return 身份证格式错误原因
     */
    public static String IDCardValidate(String IDStr) {
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            return "身份证号码长度应该为15位或18位。";
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (!isNumeric(Ai)) {
            return "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDate(strYear + strMonth + strDay)) {
            return "身份证生日无效。";
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + strMonth + strDay).getTime()) < 0) {
                return "身份证生日不在有效范围。";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return "身份证月份无效";
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return "身份证日期无效";
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            return "身份证地区编码错误。";
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi += Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (!Ai.equalsIgnoreCase(IDStr)) {
                return "身份证无效，不是合法的身份证号码";
            }
        }
        return null;
    }

    public static boolean isIDCardValidate(String IDStr) {
        return IDCardValidate(IDStr) == null;
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    public static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 复制文本到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyTextToClipboard(Context context, String text) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text);
    }


    /**
     * 显示或隐藏输入法
     *
     * @param context
     * @param v       为接受软键盘输入的视图
     */
    public static void hideOrShowSoftKeyboard(Context context, View v, boolean hide) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hide) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        } else {
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 隐藏输入法
     *
     * @param activity 当前activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View localView = activity.getCurrentFocus();
            if (localView != null && localView.getWindowToken() != null) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) BaseApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 是否是闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 4 == 0)
            return true;
        return false;
    }

    /**
     * 获取当前系统时间 年月日时分
     */

    public static int[] getCurrentTimes() {
        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int[] currentTimes = new int[]{year, month, day, hour, minute};
        return currentTimes;
    }

    /**
     * 判断给定的float的小数部分是否是0，为0则返回整数部分的字符串形式，不为0则返回此float的字符串形式
     *
     * @param prices
     * @return
     */
    public static String getFloat(float prices) {
        float price = (float) (Math.round(prices * 100)) / 100; //先让其保留2位小数精度
        if ((price - (int) price) == 0) {
            return String.valueOf((int) price);
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(price);
    }

    /**
     * 递归删除一个文件或者文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) return;
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    /**
     * 获得应用名
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * Returns true if the URI is a path to a local file or a reference to a local file.
     *
     * @param uri The uri to test.
     */
    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return "file".equals(scheme);
    }

    //    返回手机型号
    public static String getModelPhone() {
        return Build.MODEL;
    }

    /**
     * 调起手机内某个已经安装的地图app。
     *
     * @param context
     * @param lat
     * @param lng
     * @param locName
     */
    public static void startMapForLoc(Context context, double lat, double lng, String locName) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("geo:");
            sb.append(lat);
            sb.append(",");
            sb.append(lng);
            sb.append("?q=");
            sb.append(locName);
            Uri mUri = Uri.parse(sb.toString());
            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
            context.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "设备内未找到已安装的地图软件", Toast.LENGTH_SHORT).show();
        }
    }

    //检测支付宝是否存在
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 获得状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 检查是否支持NFC
     */
    public static boolean hasNFCDevice(Context context) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        return mNfcAdapter != null;
    }

    /**
     * nfc是否打开，否则跳转设置
     *
     * @param context
     * @return
     */
    public static boolean isNfcEnabled(Context context) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (mNfcAdapter == null) return false;
        if (!mNfcAdapter.isEnabled()) {
            Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
            context.startActivity(setNfc);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否是指定格式的日期
     *
     * @param datevalue  字符串
     * @param dateFormat 日期格式
     * @return
     */
    public static boolean isDateString(String datevalue, String dateFormat) {
        if (TextUtils.isEmpty(datevalue)) {
            return false;
        }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
            Date dd = fmt.parse(datevalue);
            if (datevalue.equals(fmt.format(dd))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private ArrayList<String> getAllDays(int year, int month, int day) {
        ArrayList<String> allDays = new ArrayList<>();
        for (int i = month; i <= 12; i++) {
            ArrayList<String> monthDays = getDaysForMonth(year, i, day);
            allDays.addAll(monthDays);
        }
        return allDays;
    }

    private ArrayList<String> getDaysForMonth(int year, int month, int day) {
        ArrayList<String> days = new ArrayList<>();

        int lastDay = month == ActivityUtil.getMonth() ? day : 1;
        String mMonth = month < 10 ? "0" + month : "" + month;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for (int i = lastDay; i <= 31; i++) {
                    String mDay = i < 10 ? "0" + i : "" + i;
                    days.add(year + "-" + mMonth + "-" + mDay);
                }
                break;
            case 2:
                boolean isleepyear = isLeapYear(year);
                int munday = isleepyear ? 29 : 28;
                for (int i = lastDay; i <= munday; i++) {
                    String mDay = i < 10 ? "0" + i : "" + i;
                    days.add(year + "-" + mMonth + "-" + mDay);
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for (int i = lastDay; i <= 30; i++) {
                    String mDay = i < 10 ? "0" + i : "" + i;
                    days.add(year + "-" + mMonth + "-" + mDay);
                }
                break;
        }
        return days;
    }

    public static boolean isOPenGps() {

        LocationManager locationManager = (LocationManager) BaseApplication.getInstance()
                .getSystemService(Context.LOCATION_SERVICE);

        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）

        boolean gps = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）

        // boolean network = locationManager
        // .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // 只判断GPS卫星
        if (gps/* || network */) {

            return true;

        }

        return false;

    }

    /**
     * 获取随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * MD5
     * @param input
     * @return
     */
    public static String Md5(String input) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        m.update(input.getBytes(), 0, input.length());
        byte p_md5Data[] = m.digest();
        String mOutput = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);

            if (b <= 0xF)
                mOutput += "0";
            mOutput += Integer.toHexString(b);
        }
        return mOutput.toUpperCase();
    }

    /**
     * SHA加密
     *
     * @param strSrc 明文
     * @return 加密之后的密文
     */
    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts 数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String getMMDDWithCString(String date) {
        try {
            String sDate = convertDateToStandardFormat(date);
            Date d = yyyyMMdd().parse(sDate);
            String md = MMddC().format(d);
            return md;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToStandardFormat(String date) {
        date = date.trim();
        if (date.length() == 8) {
            return date;
        } else if (date.length() == 10) {
            return date.replace("-", "");
        } else {
            return null;
        }
    }

    public static SimpleDateFormat yyyyMMdd() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    }

    public static SimpleDateFormat MMddC() {
        return new SimpleDateFormat("MM月dd日", Locale.getDefault());
    }

}
