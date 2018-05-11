package com.example.rui.myapplication.cons;

/**
 * SharedPreferences 的key值
 */

public interface SPCon {

    String UUID = "uuid";
    /**
     * 帐号 (String)
     */
    String ACCOUNT = "account";
    String USERACCOUNT = "UserAccount";
    /**
     * 密码(String)
     */
    String PASSWORD = "password";
    String USERPASSWORD = "UserPassword";
    /**
     * 是否开启每次都输入密码验证(boolean)
     */
    String ISNEEDLOGINALWAYS = "isNeedLoginAlways";

    /**
     * 判断是否有更新的数据
     */
    String isUpdate = "ISUPDATE";
}
