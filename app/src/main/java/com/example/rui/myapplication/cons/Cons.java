package com.example.rui.myapplication.cons;

import android.os.Environment;

import com.example.rui.myapplication.bean.UserInfo;

/**
 * Created by Administrator on 2018/1/10/010.
 */

public class Cons {
    // 刷新账号广播
    public static final String REFRESH_LOGIN = "REFRESH_LOGIN";

    public static UserInfo userInfo;
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath();

    public static String UID = "";

    public static String BASE_ADDRESS = "";

    public static String pwd = "";
    public static String ACCOUNT = "test";

    /**
     * 数据库文件夹路径
     */
    public static String DB_PATH = "";

    /**
     * 数据库、文件上传下载，临时文件的存储路径
     */
    public static String TEMP_PATH = "";

    /**
     * 下载临时文件
     */
    public static String TEMP_DOWN_FILE_NAME = "tempDown.txt";
    ;

    /**
     * 应用文件文件文件夹路径
     */
    public static String FILES_PATH = "";

    /**
     * 图片后缀名
     */

    public static final String IMG_SUFFIX = ".png";

    /**
     * 纬度
     */
    public static String lat = "";
    /**
     * 经度
     */
    public static String lng = "";

    public static final int LOADING_DISMISS = 10;
}
