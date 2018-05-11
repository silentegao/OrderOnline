package com.example.rui.myapplication.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2018/1/11/011.
 */

public class UUIDUtil {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 生成四位随机数
     *
     * @return
     */
    public static String getRandom4() {
       return String.valueOf ((int) ((Math.random()*9+1)*1000));
//        Random random = new Random();
//        return "" + random.nextInt(10) + "" + random.nextInt(10) + "" + random.nextInt(10) + "" + random.nextInt(10);
    }
}
