package com.example.rui.myapplication.utils;

import android.util.Log;

/**
 * 打印日志的工具类
 */

public class LogUtil {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    /**
     * 对自己的app添加一个整体的标识，方便日志检索，例如App名称为DemoApp，TAG可以设置为 DemoApplog，在日志检索的时候可以用DemoApplog关键字搜索
     */
    private static final String TAG = "way";

    public static void d(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.d(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void e(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.e(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void v(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.v(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void i(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.i(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }
}
