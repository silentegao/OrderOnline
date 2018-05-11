package com.example.rui.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.GridSpacingItemDecoration;
import com.example.rui.myapplication.activity.ShopDetailActivity;
import com.example.rui.myapplication.adapter.ShopsAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.bean.ShopsBean;
import com.example.rui.myapplication.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShopsAdapter adapter;
    private List<ShopsBean> beanList = new ArrayList<>();
    private List<ShopsBean> sourceList = new ArrayList<>();

    private int layoutId = R.layout.item_shops_recyclerview;

    private EditText etSearch;

    private int[] shopsImg = {R.mipmap.shops1,R.mipmap.shops2,R.mipmap.shops3,R.mipmap.shops4,R.mipmap.shops5,R.mipmap.shops6};

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        ShopsBean bean = new ShopsBean();
        bean.setPic(0);
        bean.setFoods("蒜蓉蒸金针菇");
        bean.setName("丁一家菜馆");
        bean.setStar(5.0f);
        bean.setSells(88);
        bean.setStartPrice("23");
        bean.setRunPrice("3");
        bean.setTime("21分钟");
        bean.setWay("1.9km");
        bean.setHot("折扣商品5折起");
        beanList.add(bean);
        sourceList.add(bean);
        bean = new ShopsBean();
        bean.setPic(1);
        bean.setFoods("蒜香酱");
        bean.setName("棒约翰比萨");
        bean.setStar(4.8f);
        bean.setSells(141);
        bean.setStartPrice("20");
        bean.setRunPrice("5");
        bean.setTime("27分钟");
        bean.setWay("1.2km");
        bean.setHot("草莓果饮买一赠一");
        beanList.add(bean);
        sourceList.add(bean);
        bean = new ShopsBean();
        bean.setPic(2);
        bean.setFoods("简餐");
        bean.setName("海哥烧烤");
        bean.setStar(4.8f);
        bean.setSells(395);
        bean.setStartPrice("20");
        bean.setRunPrice("3");
        bean.setTime("23分钟");
        bean.setWay("543m");
        bean.setHot("折扣商品5折起");
        beanList.add(bean);
        sourceList.add(bean);
        bean = new ShopsBean();
        bean.setPic(3);
        bean.setFoods("招牌土鸭");
        bean.setName("留夫鸭");
        bean.setStar(5.0f);
        bean.setSells(150);
        bean.setStartPrice("30");
        bean.setRunPrice("5");
        bean.setTime("20分钟");
        bean.setWay("765m");
        bean.setHot("新用户下单立减17");
        beanList.add(bean);
        sourceList.add(bean);
        bean = new ShopsBean();
        bean.setPic(4);
        bean.setFoods("包子粥店");
        bean.setName("一品粥铺");
        bean.setStar(4.9f);
        bean.setSells(1571);
        bean.setStartPrice("15");
        bean.setRunPrice("6.5");
        bean.setTime("42分钟");
        bean.setWay("1.3km");
        bean.setHot("折扣商品2折起");
        beanList.add(bean);
        sourceList.add(bean);
        bean = new ShopsBean();
        bean.setPic(5);
        bean.setFoods("奶茶果汁");
        bean.setName("超级奶爸");
        bean.setStar(5.0f);
        bean.setSells(522);
        bean.setStartPrice("20");
        bean.setRunPrice("3.5");
        bean.setTime("23分钟");
        bean.setWay("887km");
        bean.setHot("特价商品2元起");
        beanList.add(bean);
        sourceList.add(bean);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_shops);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ShopsAdapter(getContext(),beanList,layoutId);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ShopDetailActivity.class);
                intent.putExtra("TITLE",beanList.get(position).getName());
                intent.putExtra("POSITION",position);
                startActivity(intent);
            }
        });

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtils.dp2px(getContext(), 0), DensityUtils.dp2px(getContext(), 8)));

        recyclerView.setAdapter(adapter);

        etSearch = (EditText)view.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString();
                List<ShopsBean> list = new ArrayList<>();
                for (ShopsBean bean :
                        sourceList) {
                    if (bean.getName().contains(key)){
                        list.add(bean);
                    }
                }
                beanList.clear();
                beanList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
