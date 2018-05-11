package com.example.rui.myapplication.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleHolder;
import com.example.rui.myapplication.bean.ShopsBean;

import java.util.List;

public class ShopsAdapter extends BaseRecycleAdapter<ShopsBean> {

    private int[] shopsImg = {R.mipmap.shops1,R.mipmap.shops2,R.mipmap.shops3,R.mipmap.shops4,R.mipmap.shops5,R.mipmap.shops6};

    public ShopsAdapter(Context context, List<ShopsBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, int position, ShopsBean shopsBean) {
        baseRecycleHolder.setImageViewResourceId(R.id.item_img,
                shopsImg[shopsBean.getPic() >= shopsImg.length ? 0 : shopsBean.getPic()])
                .setTextViewText(R.id.item_title,shopsBean.getName())
                .setTextViewText(R.id.tv_star,shopsBean.getStar()+"")
                .setTextViewText(R.id.tv_peisong,"起送 ¥"+shopsBean.getStartPrice()+" | 配送 ¥"+shopsBean.getRunPrice())
                .setTextViewText(R.id.tv_time,shopsBean.getTime()+" | "+shopsBean.getWay())
                .setTextViewText(R.id.tv_foods,shopsBean.getFoods())
                .setTextViewText(R.id.tv_hot,shopsBean.getHot())
                .setTextViewText(R.id.tv_sells,"月销"+shopsBean.getSells());
        ((RatingBar)baseRecycleHolder.getView(R.id.item_rating)).setRating(shopsBean.getStar());
    }

    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder) {

    }
}
