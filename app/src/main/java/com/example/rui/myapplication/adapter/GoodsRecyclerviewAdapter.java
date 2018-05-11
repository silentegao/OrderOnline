package com.example.rui.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.activity.DetailActivity;
import com.example.rui.myapplication.bean.GoodsInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19/019.
 */

public class GoodsRecyclerviewAdapter extends RecyclerView.Adapter<GoodsRecyclerviewAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;

    List<GoodsInfo> lstGoodsInfo;
    int[] imgIDs = {R.drawable.star, R.drawable.pizza, R.drawable.ha};

    public GoodsRecyclerviewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        lstGoodsInfo = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.goods_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GoodsInfo goodsInfo = lstGoodsInfo.get(position);
        final int imageUrl = goodsInfo.getImageUrl();
        final String name = goodsInfo.getGoodsName();
        final long price = goodsInfo.getGoodsPrice();
        final int goodsNumbers = goodsInfo.getGoodsAmount();
        final String detail = goodsInfo.getGoodsInfo();
        final float star = Float.parseFloat(goodsInfo.getGoodsLevel());
//        Picasso.with(context).load("111").error(imageUrl)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .into(holder.itemImg);
        holder.itemImg.setImageResource(imageUrl);
        holder.itemTitle.setText(name);
        holder.itemMinPrice.setText(String.valueOf(price));
        holder.itemEvaPerMonth.setText(String.valueOf(goodsNumbers));
        holder.itemGoodsInfo.setText(detail);
        holder.itemRating.setRating(star);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ArchivesDetailActivity.class);
//                intent.putExtra("uid", userBase.getUid());
//                context.startActivity(intent);
                int id = 0;
                if (goodsInfo != null){
                    try {
                        id = goodsInfo.getId();
                    }catch (Exception e){}
                }
                DetailActivity.actionStart(context,id,
                        imageUrl,name,star,price,goodsNumbers,detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstGoodsInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemTitle;
        RatingBar itemRating;
        TextView itemEvaPerMonth;
        TextView itemNumberPerMonth;
        TextView itemDistance;
        TextView itemMinPrice;
        TextView itemExtraPrice;
        ImageView itemZhuan;
        ImageView itemSu;
        ImageView itemPiao;
        TextView itemMan;
        LinearLayout itemManTotal;
        TextView itemDi;
        LinearLayout itemDiTotal;
        TextView itemGoodsInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImg = (ImageView) itemView.findViewById(R.id.item_img);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemRating = (RatingBar) itemView.findViewById(R.id.item_rating);
            itemEvaPerMonth = (TextView) itemView.findViewById(R.id.item_eva_per_month);
            itemNumberPerMonth = (TextView) itemView.findViewById(R.id.item_number_per_month);
            itemDistance = (TextView) itemView.findViewById(R.id.item_distance);
            itemMinPrice = (TextView) itemView.findViewById(R.id.item_min_price);
            itemExtraPrice = (TextView) itemView.findViewById(R.id.item_extra_price);
            itemZhuan = (ImageView) itemView.findViewById(R.id.item_zhuan);
            itemSu = (ImageView) itemView.findViewById(R.id.item_su);
            itemPiao = (ImageView) itemView.findViewById(R.id.item_piao);
            itemMan = (TextView) itemView.findViewById(R.id.item_man);
            itemManTotal = (LinearLayout) itemView.findViewById(R.id.item_man_total);
            itemDi = (TextView) itemView.findViewById(R.id.item_di);
            itemDiTotal = (LinearLayout) itemView.findViewById(R.id.item_di_total);
            itemGoodsInfo = (TextView) itemView.findViewById(R.id.tv_goods_info);
        }
    }

    interface OnItemClick {
        void onClick();
    }

    public void setData(List<GoodsInfo> lstGoodsInfoTemp) {
        lstGoodsInfo.clear();
        if (lstGoodsInfoTemp != null) {
            lstGoodsInfo.addAll(lstGoodsInfoTemp);
        }
        notifyDataSetChanged();
    }

    public void addData(List<GoodsInfo> lstGoodsInfoTemp) {

        if (lstGoodsInfoTemp != null) {
            lstGoodsInfo.addAll(lstGoodsInfoTemp);
        }
        notifyDataSetChanged();
    }
}
