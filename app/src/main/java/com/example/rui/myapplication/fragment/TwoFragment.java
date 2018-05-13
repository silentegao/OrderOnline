package com.example.rui.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.GridSpacingItemDecoration;
import com.example.rui.myapplication.activity.OrderListDetailActivity;
import com.example.rui.myapplication.adapter.OrderListAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.bean.OrdersInfo;
import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.utils.DensityUtils;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

public class TwoFragment extends Fragment {
    private static final String TAG = "life";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderListAdapter adapter;
    private List<OrdersInfo> ordersInfoList = new ArrayList<>();
    private int layoutIds = R.layout.orderinfo_recyclerview_item;

    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.two_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_orders);

        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtils.dp2px(getContext(), 0), DensityUtils.dp2px(getContext(), 8)));

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "two onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "two onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        select();
        Log.e(TAG, "onResume: ");
    }

    private void select() {
        List<OrdersInfo> list = DataSupport.where("orderUserUid = ? ", Cons.UID).order("createTime desc").find(OrdersInfo.class);
        ordersInfoList.clear();
        if (list != null) {
            ordersInfoList.addAll(list);
        }
        if (adapter == null) {
            adapter = new OrderListAdapter(getContext(), ordersInfoList, layoutIds);
            adapter.setDeleteOrderInterface(new OrderListAdapter.DeleteOrderInterface() {
                @Override
                public void delete(int position) {
                    Log.e("infoD", "delete: ID:" + ordersInfoList.get(position).getId());
                    DataSupport.delete(OrdersInfo.class, ordersInfoList.get(position).getId());
                    ordersInfoList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                Toast.makeText(getContext(), "ceshi!"+position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), OrderListDetailActivity.class);
                    intent.putExtra("ImageUrl", ordersInfoList.get(position).getImageUrl());
                    intent.putExtra("ImagePicName", ordersInfoList.get(position).getImagePicName());
                    intent.putExtra("GoodsTypeName", ordersInfoList.get(position).getGoodsTypeName());
                    intent.putExtra("GoodsAmount", ordersInfoList.get(position).getGoodsAmount());
                    intent.putExtra("GoodsPrice", ordersInfoList.get(position).getGoodsPrice());
                    intent.putExtra("SeatNo", ordersInfoList.get(position).getSeatNo());
                    intent.putExtra("CreateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ordersInfoList.get(position).getCreateTime()));
                    intent.putExtra("Id", ordersInfoList.get(position).getId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "two onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "two onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "two onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "two onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "two onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "two onDetach");
    }
}
