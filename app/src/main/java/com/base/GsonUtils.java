package com.base;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chenbaolin on 2017/4/25.
 */

public class GsonUtils {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 解析data是object的情况
     *
     * @param reader
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Result<T> fromJsonObject(Reader reader, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(Result.class, new Class[]{clazz});
        return gson.fromJson(reader, type);
    }


    /**
     * 解析data是array的情况
     *
     * @param reader
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Result<List<T>> fromJsonArray(Reader reader, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
        return gson.fromJson(reader, type);
    }
}
