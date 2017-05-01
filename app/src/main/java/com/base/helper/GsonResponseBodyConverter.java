package com.base.helper;

import com.base.exception.APIException;
import com.base.util.LogUtils;
import com.entity.HttpResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by chenbaolin on 2017/4/26.
 */

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        LogUtils.d(httpResult.getResponseCode() + "---------------");
        LogUtils.d(httpResult.getResponseDescription() + "：：HttpResult");
        if (httpResult.isTokenInvalid()) {
            value.close();
            try {
                throw new APIException(httpResult.getResponseCode(), httpResult.getResponseDescription());
            } catch (APIException e) {
                e.printStackTrace();
            } finally {
                value.close();
            }
        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

}