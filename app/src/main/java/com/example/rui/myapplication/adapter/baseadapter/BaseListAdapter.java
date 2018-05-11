package com.example.rui.myapplication.adapter.baseadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * ListViewAdapter的基类
 * 封装了对于item的获取和生成操作
 *
 * 只需要对item中的view进行赋值操作就可以了
 * Created by WUQING on 2017/2/16.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected Activity activity;
    protected List<T> datas;
    protected LayoutInflater layoutInflater;

    protected int[] layoutIds;  // 0: 默认item 1: 头Item
    protected int topN;         // topN:-1 正常列表无特殊样式, 0 第一个item样式不一样,1 头两个item样式不一样,2..... 开头的几个样式与下面的不一样, 如开头2个item样式与下面的不一样  topN:1

    protected int pagePosition; // 用来给实现类区分是第几个list,如左右滑动页中加载两个listView

    protected int selectedPosition;

    public BaseListAdapter(Context context, List<T> datas, int topN, int pagePosition, int[] layoutIds){
        this.context = context;
        this.activity = (Activity)context;
        this.datas = datas;
        this.layoutIds = layoutIds;
        this.topN = topN;
        this.pagePosition = pagePosition;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     *  普通列表
     * @param context
     * @param datas
     * @param layoutIds
     */
    public BaseListAdapter(Context context, List<T> datas, int[] layoutIds){
        this(context,datas,-1,0,layoutIds);
    }

    @Override
    public int getCount() {
        if (datas != null){
            return datas.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (datas != null){
            return datas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = BaseViewHolder.get(context,convertView,parent,position,topN,layoutIds);
        if (position <= topN) {
            convertTopN(holder, getItem(position), position);
        }else{
            convertItem(holder, getItem(position), position);
        }

        return holder.getConvertView();
    }

    public void setSelectedItem(int position){
        selectedPosition = position;
    }

    public abstract void convertTopN(BaseViewHolder holder,T t,int position);
    public abstract void convertItem(BaseViewHolder holder,T t,int position);

}
