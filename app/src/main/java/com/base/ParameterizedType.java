package com.base;


import java.lang.reflect.Type;

/**
 * Created by chenbaolin on 2017/4/24.
 */

public interface ParameterizedType extends Type {

    // 返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
    Type[] getActualTypeArguments();

    // Map<String,User>里的Map,所以返回值是Map.class
    Type getRawType();

    // 用于这个泛型上中包含了内部类的情况,一般返回null
    Type getOwnerType();

}
