package com.hbtl.yhb.modles;

public class CountModel {


    /**
     * channel_name : 销售卡渠道
     * count : 1
     * today_count : 1
     */

    private String channel_name;
    private int count;
    private int today_count;

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getToday_count() {
        return today_count;
    }

    public void setToday_count(int today_count) {
        this.today_count = today_count;
    }
}
