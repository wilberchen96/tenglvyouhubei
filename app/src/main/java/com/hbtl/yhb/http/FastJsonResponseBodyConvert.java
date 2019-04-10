package com.hbtl.yhb.http;

import com.alibaba.fastjson.JSON;
import com.hbtl.yhb.http.exception.ApiException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

public class FastJsonResponseBodyConvert<T> implements Converter<ResponseBody, Object> {

    private Type mType;

    public FastJsonResponseBodyConvert(Type type) {
        this.mType = type;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        Result result = JSON.parseObject(tempStr, mType);
        int statusCode = result.getStatus();
        if (statusCode != 0) {
            throw new ApiException(result.getMsg(), statusCode);
        } else {
            return result;
        }
    }

}
