package com.example.rui.myapplication.database;

import android.content.ContentValues;

import com.example.rui.myapplication.bean.DataBean;
import com.example.rui.myapplication.bean.GoodsInfo;
import com.example.rui.myapplication.bean.OrdersInfo;
import com.example.rui.myapplication.bean.UserInfo;
import com.example.rui.myapplication.cons.Cons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/30/030.
 */

public class DataUtil {

    private static DataUtil dataSignUtil;

    public static DataUtil getInstance() {
        if (dataSignUtil == null) {
            dataSignUtil = new DataUtil();
        }
        return dataSignUtil;
    }

    private DataUtil() {

    }

    /**
     * 保存数据
     *
     * @param s
     */
    public void saveJson(String s) {
        deleteAll();
        LitePal.getDatabase();
        Gson gson = new Gson();
        DataBean bean = gson.fromJson(s, DataBean.class);

        DataSupport.saveAll(bean.user_info);
        DataSupport.saveAll(bean.goods_info);
        DataSupport.saveAll(bean.order_info);
    }

    public void saveJson() {
        deleteAll();
        LitePal.getDatabase();
        DataBean bean = new DataBean();

        DataSupport.saveAll(bean.user_info);
        DataSupport.saveAll(bean.goods_info);
        DataSupport.saveAll(bean.order_info);
    }

    public void deleteAll() {
        LitePal.deleteDatabase(Cons.DB_PATH);
    }

    public String getGosn() {
        List<UserInfo> userInfo = DataSupport.where("isUpdate = ?", "1").find(UserInfo.class);
        List<GoodsInfo> goodsInfo = DataSupport.where("isUpdate = ?", "1").find(GoodsInfo.class);
        List<OrdersInfo> ordersInfo = DataSupport.where("isUpdate = ?", "1").find(OrdersInfo.class);
//
//        int size = doctor.size() +
//                middleServeDetail.size() + middleUserDisease.size() + middleUserFamilyDisease.size()
//                + middleUserPopulate.size() +
//                middleUserSignPopulate.size() + middleUserSignServe.size() + sign.size() + sysAccount.size() +
//                sysAccount.size() + userBase.size() + userBase.size() + userInfo.size() + visit1to8mNeonate.size() +
//                visit2to5tAntenatal.size() + visit2to5tAntenatal.size() +
//
//        if (size == 0) {
//            return "";
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("user_info", userInfo);
        map.put("goods_info", goodsInfo);
        map.put("orders_info", ordersInfo);
        GsonBuilder gsonbuilder = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = gsonbuilder.create();
        return gson.toJson(map);
    }

    public void changeToFinsh() {
        ContentValues values = new ContentValues();
        values.put("isUpdate", "0");
        DataSupport.updateAll(UserInfo.class, values, "isUpdate = 1");
        DataSupport.updateAll(GoodsInfo.class, values, "isUpdate = 1");
        DataSupport.updateAll(OrdersInfo.class, values, "isUpdate = 1");
    }

}
