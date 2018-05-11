package com.example.rui.myapplication.adapter.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by WUQING on 2017/9/12.
 */

public class BaseRecycleHolder extends RecyclerView.ViewHolder {

    private Context context;
    private SparseArray<View> mViews;   // 弱引用,内存不足时自动释放
    public BaseRecycleHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.mViews = new SparseArray<View>();
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T)view;
    }




    public BaseRecycleHolder setImageViewURI(int viewId, String url, int resId){
        ImageView iv = getView(viewId);
        setImageViewURI(iv,url,resId);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(ImageView iv, String url, int resId){
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,resId);
//        new ImageViewUtils(context).setImageViewURL(iv,url,resId);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(int viewId, String url, Drawable drawable){
        ImageView iv = getView(viewId);
        setImageViewURI(iv,url,drawable);
        return this;
    }

    public BaseRecycleHolder setImageViewResourceId(int viewId,int resourId){
        ((ImageView)getView(viewId)).setImageResource(resourId);
        return this;
    }

    public BaseRecycleHolder setImageViewURI(ImageView iv, String url, Drawable drawable){
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,drawable);
//        new ImageViewUtils(context).setImageViewURL(iv,url,drawable);
        return this;
    }

    public BaseRecycleHolder setImageViewBitmap(int viewId,Bitmap bitmap){
        ((ImageView)getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public BaseRecycleHolder setTextViewText(int viewId,String txt){
        TextView tv = getView(viewId);
        tv.setText(txt);
        return this;
    }

    public BaseRecycleHolder setViewTag(int viewId,Object tag){
        getView(viewId).setTag(tag);
        return this;
    }

    public BaseRecycleHolder setVisable(int viewId,int visible){
        getView(viewId).setVisibility(visible);
        return this;
    }

    public BaseRecycleHolder setTextViewTextColor(int viewId,int id){
        ((TextView)getView(viewId)).setTextColor(context.getResources().getColor(id));
        return this;
    }

    public BaseRecycleHolder setTextViewTextColor(int viewId,String colorVar) throws Exception {
        ((TextView)getView(viewId)).setTextColor(Color.parseColor(colorVar));
        return this;
    }
}
