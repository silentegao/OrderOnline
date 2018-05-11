package com.example.rui.myapplication.adapter.baseadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 *
 * Viewholder 类的封装
 * 适用于所有View的读取操作
 * 通常配合 BaseListAdapter BaseGrildViewAdapter 操作
 * Created by WUQING on 2017/2/16.
 */

public class BaseViewHolder {

    private SparseArray<View> mViews;   // 弱引用,内存不足时自动释放
    private int position;
    private View convertView;

    private int topN;
    private static final int TOPITEM = 0;  // 有头样式的item
    private static final int NORMALITEM = 1;   // 正常的Item
    // 用来区分是否需要创建, 当滚动列表时,出现的列表就是隐藏的列表的复用,这其中就包括头部View
    // 如果不用tag来区分,当复用到头部view时,因为其结构不一样会导致错误,
    // 这里需要通过tag来区分,当复用到头部view时,会重新再创建新的view.
    private int nowTag = NORMALITEM;

    private Context context;
    private Activity activity;

    public BaseViewHolder(Context context, ViewGroup viewGroup, int layoutId, int position,
                          int setTag){
        this.context = context;
        this.position = position;
        this.mViews = new SparseArray<View>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId,viewGroup,false);
        this.nowTag = setTag;
        this.convertView.setTag(this);
        this.activity = (Activity)context;
    }

    public static BaseViewHolder get(Context context, View convertView, ViewGroup viewGroup,
                                     int position, int topN, int[] layoutIds){
        if (position <= topN){ // 有头部样式
            int layoutId = layoutIds.length > 1 ? layoutIds[1] : layoutIds[0] ;
            return getHolder(context,convertView,viewGroup,position,topN,NORMALITEM,TOPITEM,layoutId);
        }else{  // 普通样式
            return getHolder(context,convertView,viewGroup,position,topN,TOPITEM,NORMALITEM,layoutIds[0]);
        }
    }

    /**
     *
     * @param context
     * @param convertView
     * @param viewGroup
     * @param position
     * @param topN
     * @param getTag        用来判断当前的这个View类别是不是其他的类别,如果是则创建,否则不创建
     *                      当前是头部样式时,我就判断是否是正常item, 当前是正常item时,就判断是否是头部样式
     * @param setTag
     * @param layoutId
     * @return
     */
    private static BaseViewHolder getHolder(Context context, View convertView, ViewGroup viewGroup,
                                            int position, int topN, int getTag, int setTag,
                                            int layoutId){
        boolean isCreate = false;
        BaseViewHolder holder = null;
        if (convertView == null){
            isCreate = true;
        }else{
            holder = (BaseViewHolder)convertView.getTag();
            holder.position = position;
            if (holder.nowTag == getTag){
                isCreate = true;
            }
        }

        if (isCreate){
            return new BaseViewHolder(context,viewGroup,layoutId,position,setTag);
        }else{
            return holder;
        }
    }


    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T)view;
    }

    public View getConvertView(){
        return convertView;
    }



    // ---------------- 给各种控件赋值

    public BaseViewHolder setTextViewText(int viewId,String txt){
        TextView tv = getView(viewId);
        tv.setText(txt);
        return this;
    }

    public BaseViewHolder setVisable(int viewId,int visible){
        getView(viewId).setVisibility(visible);
        return this;
    }

    public BaseViewHolder setTextViewUnLine(int viewId){
        ((TextView)getView(viewId)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        return this;
    }

    public BaseViewHolder setGridViewAdapter(int viewId, BaseListAdapter adapter){
        ((GridView)getView(viewId)).setAdapter(adapter);
        return this;
    }

    public BaseViewHolder setTextViewTextColor(int viewId,int id){
        ((TextView)getView(viewId)).setTextColor(context.getResources().getColor(id));
        return this;
    }

    public BaseViewHolder setTextViewTextColor(int viewId,String colorVar) throws Exception {
        ((TextView)getView(viewId)).setTextColor(Color.parseColor(colorVar));
        return this;
    }


    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener clicklistener){
        getView(viewId).setOnClickListener(clicklistener);
        return this;
    }



    public BaseViewHolder setGridViewOnItemClick(int viewId, AdapterView.OnItemClickListener onItemClickListener){
        ((GridView)getView(viewId)).setOnItemClickListener(onItemClickListener);
        return this;
    }


    public BaseViewHolder setEnable(int viewId,boolean enable){
        getView(viewId).setEnabled(enable);
        return this;
    }


    public BaseViewHolder setBackgroundResource(int viewId,int resource){
        getView(viewId).setBackgroundResource(resource);
        return this;
    }

    public BaseViewHolder setBackgroundDrawable(int viewId,Drawable drawable){
        getView(viewId).setBackgroundDrawable(drawable);
        return this;
    }

    public BaseViewHolder addHorizontalScrollViewChild(int viewId,View view){
        ((HorizontalScrollView)getView(viewId)).addView(view);
        return this;
    }

    public BaseViewHolder addLinearLayoutViewChild(int viewId,View view){
        ((LinearLayout)getView(viewId)).addView(view);
        return this;
    }

    public BaseViewHolder setImageViewRes(int viewId,int resId){
        ((ImageView)getView(viewId)).setImageResource(resId);
        return this;
    }

    public BaseViewHolder setImageViewBitmap(int viewId,Bitmap bitmap){
        ((ImageView)getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setImageViewDrawable(int viewId, Drawable drawable){
        ((ImageView)getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageViewURI(int viewId, String url, int resId){
        ImageView iv = getView(viewId);
        setImageViewURI(iv,url,resId);
        return this;
    }

    public BaseViewHolder setImageViewURI(ImageView iv, String url, int resId){
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,resId);
//        new ImageViewUtils(context).setImageViewURL(iv,url,resId);
        return this;
    }

    public BaseViewHolder setImageViewURI(int viewId, String url, Drawable drawable){
        ImageView iv = getView(viewId);
        setImageViewURI(iv,url,drawable);
        return this;
    }

    public BaseViewHolder setImageViewURI(ImageView iv, String url, Drawable drawable){
//        ImageViewUtils.getInstance(context).setImageViewURL(iv,url,drawable);
//        new ImageViewUtils(context).setImageViewURL(iv,url,drawable);
        return this;
    }

    public BaseViewHolder setTextViewPaint(int viewId){
        ((TextView)getView(viewId)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return this;
    }

    // 在这里设置会有问题  错位设置不进去
    public BaseViewHolder setRatingBarNums(int viewId,float star){
        ((RatingBar)getView(viewId)).setRotation(star);
        return this;
    }

    public BaseViewHolder setCheckBoxChecked(int viewId,boolean checked){
        ((CheckBox)getView(viewId)).setChecked(checked);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener){
        ((CheckBox)getView(viewId)).setOnCheckedChangeListener(listener);
        return this;
    }

    public BaseViewHolder setTextViewTextSize(int viewId,float size){
        ((TextView)getView(viewId)).setTextSize(size);
        return this;
    }

}
