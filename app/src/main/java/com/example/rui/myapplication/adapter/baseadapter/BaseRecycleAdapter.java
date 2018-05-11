package com.example.rui.myapplication.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WUQING on 2017/9/12.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleHolder> {

    protected Context context;
    protected List<T> list;
    protected int layoutId;
    protected int nowPosition;

    private boolean isRequestFocus = false;

    public BaseRecycleAdapter(Context context, List<T> list, int layoutId){
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    public void setNowPosition(int nowPosition){
        this.nowPosition = nowPosition;
        isRequestFocus = true;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public interface OnItemSelectListener{
        void onItemSelected(View view, int position);
        void onItemDisSelected(View view, int position);
    }

    public interface OnItemKeyListener{
        boolean onItemLeft(View view, int position);
        boolean onItemRight(View view, int position);
        boolean onItemUp(View view, int position);
        boolean onItemDown(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemSelectListener onItemSelectListener;
    private OnItemKeyListener onItemKeyListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }
    public void setOnItemKeyListener(OnItemKeyListener onItemKeyListener){
        this.onItemKeyListener = onItemKeyListener;
    }

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(layoutId,viewGroup,false);
        BaseRecycleHolder viewHolder = new BaseRecycleHolder(itemView,context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseRecycleHolder baseRecycleHolder,final int position) {
        bindView(baseRecycleHolder,position,list.get(position));
        baseRecycleHolder.itemView.setFocusable(true);

        if (position == nowPosition && isRequestFocus){
            nowStyle(baseRecycleHolder);
            baseRecycleHolder.itemView.requestFocus();
        }

        baseRecycleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(baseRecycleHolder.itemView,position);
                }
            }
        });
        baseRecycleHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    if (onItemSelectListener != null){
                        onItemSelectListener.onItemSelected(baseRecycleHolder.itemView,position);
                    }
                }else{
                    if (onItemSelectListener != null){
                        onItemSelectListener.onItemDisSelected(baseRecycleHolder.itemView,position);
                    }
                }
            }
        });
        baseRecycleHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        onItemKeyListener != null) {
                    switch (keyCode){
                        case KeyEvent.KEYCODE_DPAD_UP:
                            return onItemKeyListener.onItemUp(baseRecycleHolder.itemView,position);
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return onItemKeyListener.onItemDown(baseRecycleHolder.itemView,position);
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            return onItemKeyListener.onItemLeft(baseRecycleHolder.itemView,position);
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            return onItemKeyListener.onItemRight(baseRecycleHolder.itemView,position);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public abstract void bindView(BaseRecycleHolder baseRecycleHolder,int position,T t);
    public abstract void nowStyle(BaseRecycleHolder baseRecycleHolder);
}
