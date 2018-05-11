package com.example.rui.myapplication.adapter;

import android.content.Context;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleHolder;

import java.util.List;

public class SeatListAdapter extends BaseRecycleAdapter<String> {

    public SeatListAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindView(BaseRecycleHolder baseRecycleHolder, int position, String s) {
        baseRecycleHolder.setTextViewText(R.id.tv_seat,s);
    }

    @Override
    public void nowStyle(BaseRecycleHolder baseRecycleHolder) {

    }
}
