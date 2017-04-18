package com.base.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.util.Collection;

/**
 * Created by chenbaolin on 2017/4/18.
 */

public class ValidateUtils {

    /**
     * 手机号码验证
     *
     * @param mobile
     * @return
     */
    public static Boolean isMobileNo(String mobile) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(telRegex)) return false;
        else return mobile.matches(telRegex);

    }


    /**
     * 泛型非空判断
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNullOrEmpty(T t) {
        if (t instanceof Bitmap) {
            return isNullOrEmpty((Bitmap) t);
        } else if (t instanceof String) {
            return isNullOrEmpty((String) t);
        } else if (t instanceof Collection) {
            return isNullOrEmpty((Collection<?>) t);
        } else {
            return t == null;
        }
    }

    /**
     * 数组非空判断
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> boolean isNullOrEmpty(T[] arr) {
        return (arr == null) || (arr.length == 0);
    }

    private static <T> boolean isNullOrEmpty(Collection<T> c) {
        return (c == null) || (c.size() == 0);
    }

    private static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0);
    }

    private static boolean isNullOrEmpty(Bitmap bmp) {
        return (bmp == null) || (bmp.isRecycled());
    }

}
