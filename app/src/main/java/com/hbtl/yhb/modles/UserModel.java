package com.hbtl.yhb.modles;

import java.io.Serializable;

public class UserModel implements Serializable {
    /**
     * token : 42ff210ab40970b205f98252c0d024beedb2dab1
     * nick_name : test
     */

    private String token;
    private String nick_name;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
