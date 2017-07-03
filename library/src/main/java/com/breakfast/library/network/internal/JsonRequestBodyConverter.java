package com.breakfast.library.network.internal;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by Steven on 2017/4/8.
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        TypeAdapter<T> adapter1 = adapter;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        String postBody = gson.toJson(value); //对象转化成json
        return RequestBody.create(MEDIA_TYPE, postBody);
    }
}