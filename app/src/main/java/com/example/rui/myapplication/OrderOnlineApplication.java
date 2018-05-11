package com.example.rui.myapplication;


import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.cons.SPCon;
import com.example.rui.myapplication.utils.SPUtils;

import org.litepal.LitePalApplication;
import org.xutils.x;

/**
 * Created by Administrator on 2018/1/10/010.
 */

public class OrderOnlineApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        Cons.UID = (String) SPUtils.get(this, SPCon.UUID, "");
        Cons.ACCOUNT = (String) SPUtils.get(this, SPCon.ACCOUNT, "");
    }

}
