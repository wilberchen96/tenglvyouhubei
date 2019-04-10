package com.hbtl.yhb.http.exception;

/**
 * Created by tiano on 2019/2/12.
 */
public class ApiException extends RuntimeException {


    private int statusCode;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
