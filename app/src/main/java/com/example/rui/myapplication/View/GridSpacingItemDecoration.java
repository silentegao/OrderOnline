package com.example.rui.myapplication.View;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/11/14/014.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int leftspacing;

    private int bottomspacing;

    public GridSpacingItemDecoration(int spanCount, int leftspacing, int bottomspacing) {
        this.spanCount = spanCount;
        this.leftspacing = leftspacing;
        this.bottomspacing = bottomspacing;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = leftspacing;
        outRect.bottom = bottomspacing;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        int position = parent.getChildLayoutPosition(view);
        if (position == 0 || position == 1 | position == 2) {
            outRect.top = leftspacing;
        }
        if ((parent.getChildLayoutPosition(view) + 1) % spanCount == 0) {
            outRect.right = leftspacing;
        }

    }
}
