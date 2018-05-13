package com.example.rui.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.GridSpacingItemDecoration;
import com.example.rui.myapplication.adapter.GoodsRecyclerviewAdapter;
import com.example.rui.myapplication.bean.GoodsInfo;
import com.example.rui.myapplication.utils.DensityUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

public class OneFragment extends Fragment {
    private static final String TAG = "life";


    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    EditText etSearch;

    private TextView[] tvHots = new TextView[4];
    private int[] ids = {R.id.tv_hots, R.id.tv_hot0, R.id.tv_hot1, R.id.tv_hot2};

    GridLayoutManager gridLayoutManager;
    GoodsRecyclerviewAdapter goodsRecyclerviewAdapter;
    Unbinder unbinder;
    GoodsInfo goodsInfo;
    List<GoodsInfo> lstGoodsInfo;
    List<GoodsInfo> lstGoodsLitePal;
    private List<GoodsInfo> sourceList = new ArrayList<>();
    private String getTitle;
    private int getPosition;

    private String[] shopNames = {"丁一家菜馆", "棒约翰比萨", "海哥烧烤", "留夫鸭", "一品粥铺", "超级奶爸"};

    private String detail = "很好吃,特别优惠,满100送50,快来订购吧";

    private int[] shop1_pics = {R.mipmap.shop1_1, R.mipmap.shop1_2, R.mipmap.shop1_3, R.mipmap.shop1_4};
    private String[] shop1_names = {"蒜蓉蒸金针菇", "豆渣饼烧杂鱼", "鲜藤椒鱼", "口水鸡"};
    private float[] shop1_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop1_price = {14, 38, 30, 23};
    private int[] shop1_num = {33, 123, 60, 34};

    private int[] shop2_pics = {R.mipmap.shop2_1, R.mipmap.shop2_2, R.mipmap.shop2_3, R.mipmap.shop2_4};
    private String[] shop2_names = {"榴莲芝士条传统小装6", "草莓果饮买一赠一", "9寸传统超级棒约翰（夜宵）", "缤纷烤翅拼盘"};
    private float[] shop2_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop2_price = {32, 44, 76, 52};
    private int[] shop2_num = {33, 123, 60, 34};

    private int[] shop3_pics = {R.mipmap.shop3_1, R.mipmap.shop3_2, R.mipmap.shop3_3, R.mipmap.shop3_4};
    private String[] shop3_names = {"纯手工自制羊肉串", "金针菇", "五花肉（原味）", "热狗"};
    private float[] shop3_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop3_price = {4, 3, 3, 2};
    private int[] shop3_num = {33, 123, 60, 34};

    private int[] shop4_pics = {R.mipmap.shop4_1, R.mipmap.shop4_2, R.mipmap.shop4_3, R.mipmap.shop4_4};
    private String[] shop4_names = {"招牌土鸭", "香辣蟹钳", "香辣虾", "土鸭掌"};
    private float[] shop4_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop4_price = {38, 238, 40, 77};
    private int[] shop4_num = {33, 123, 60, 34};

    private int[] shop5_pics = {R.mipmap.shop5_1, R.mipmap.shop5_2, R.mipmap.shop5_3, R.mipmap.shop5_4};
    private String[] shop5_names = {"大号韭菜粉丝盒子", "喷油咸鸭蛋", "手工豇豆饼", "皮蛋瘦肉粥（一品）"};
    private float[] shop5_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop5_price = {31, 38, 420, 17};
    private int[] shop5_num = {33, 123, 60, 34};

    private int[] shop6_pics = {R.mipmap.shop6_1, R.mipmap.shop6_2, R.mipmap.shop6_3, R.mipmap.shop6_4};
    private String[] shop6_names = {"超级大鸡排", "鸡翅包饭", "双拼奶茶", "大满贯"};
    private float[] shop6_star = {4.1f, 3.4f, 5.0f, 4.3f};
    private int[] shop6_price = {11, 65, 43, 77};
    private int[] shop6_num = {33, 123, 60, 34};

    public static OneFragment newInstance(String title, int n) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putInt("POSITION", n);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "one onCreate");
        if (getArguments() != null) {
            getTitle = getArguments().getString("TITLE");
            getPosition = getArguments().getInt("POSITION");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.d(TAG, "one onCreateView");

        for (int i = 0; i < tvHots.length; i++) {
            tvHots[i] = (TextView) view.findViewById(ids[i]);
            tvHots[i].setOnClickListener(new MyHotOnClickListener(i));
        }

        initView();
        return view;
    }

    class MyHotOnClickListener implements View.OnClickListener {

        private int position;

        public MyHotOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            List<GoodsInfo> list = new ArrayList<>();
            if (position == 0) {
                etSearch.setText("");
            }
            for (GoodsInfo bean :
                    sourceList) {
                if (position == 0) {
                    list.add(bean);
                } else if (position == 1) {
                    if (Float.parseFloat(bean.getGoodsLevel()) == 5.0f) {
                        list.add(bean);
                    }
                } else if (position == 2) {
                    if (bean.getGoodsAmount() >= 50) {
                        list.add(bean);
                    }
                } else if (position == 3) {
                    if (bean.getGoodsPrice() > 25) {
                        list.add(bean);
                    }
                }
            }
            lstGoodsInfo.clear();
            lstGoodsInfo.addAll(list);
            goodsRecyclerviewAdapter.setData(lstGoodsInfo);
        }
    }

    void initView() {
        tvTitle.setText(getTitle);
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
                List<GoodsInfo> list = new ArrayList<>();
                for (GoodsInfo bean :
                        sourceList) {
                    if (bean.getGoodsName().contains(key)) {
                        list.add(bean);
                    }
                }
                lstGoodsInfo.clear();
                lstGoodsInfo.addAll(list);
                goodsRecyclerviewAdapter.setData(lstGoodsInfo);
            }
        });
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rvGoods.setLayoutManager(gridLayoutManager);
        goodsRecyclerviewAdapter = new GoodsRecyclerviewAdapter(getContext());
        rvGoods.setHasFixedSize(true);
        rvGoods.setAdapter(goodsRecyclerviewAdapter);
        rvGoods.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtils.dp2px(getContext(), 0), DensityUtils.dp2px(getContext(), 8)));
        // lstGoodsInfo = DataSupport.where("status = 1").find(GoodsInfo.class);
        // sourceList.addAll(lstGoodsInfo);
        // if (lstGoodsInfo.size() == 0) {
        lstGoodsInfo = new ArrayList<>();
        initData();
        // }
        goodsRecyclerviewAdapter.setData(lstGoodsInfo);
    }

    void initData() {
        lstGoodsLitePal = DataSupport.where("shopName = ? and status = 1 order by createTime desc", getTitle).find(GoodsInfo.class);
        List<GoodsInfo> list = getList(getPosition);
        if (lstGoodsLitePal != null && lstGoodsLitePal.size() > 0) {
            if (lstGoodsLitePal.size() >= 4) {
                lstGoodsInfo.clear();
                lstGoodsInfo.addAll(lstGoodsLitePal);
                sourceList.clear();
                sourceList.addAll(lstGoodsLitePal);
            } else {
                lstGoodsInfo.clear();
                lstGoodsInfo.addAll(lstGoodsLitePal);
                lstGoodsInfo.addAll(list);
                sourceList.clear();
                sourceList.addAll(lstGoodsLitePal);
                sourceList.addAll(list);
            }

        } else {
            lstGoodsInfo.clear();
            lstGoodsInfo.addAll(list);
            sourceList.clear();
            sourceList.addAll(list);

            for (int i = 0; i < lstGoodsInfo.size(); i++) {
//            goodsInfo = DataSupport.where("status = 1").findFirst(GoodsInfo.class);
                goodsInfo = new GoodsInfo();
                goodsInfo.setShopName(lstGoodsInfo.get(i).getShopName());
                goodsInfo.setImageUrl(lstGoodsInfo.get(i).getImageUrl());
                goodsInfo.setGoodsName(lstGoodsInfo.get(i).getGoodsName());
                goodsInfo.setGoodsAmount(lstGoodsInfo.get(i).getGoodsAmount());
                goodsInfo.setGoodsPrice(lstGoodsInfo.get(i).getGoodsPrice());
                goodsInfo.setGoodsInfo(lstGoodsInfo.get(i).getGoodsInfo());
                goodsInfo.setGoodsLevel(lstGoodsInfo.get(i).getGoodsLevel());
                goodsInfo.setStatus(1);
                goodsInfo.setIsUpdate(1);
                goodsInfo.setCreateTime(new Date(System.currentTimeMillis()));
                goodsInfo.setModifyTime(new Date(System.currentTimeMillis()));
                goodsInfo.save();
            }
        }
    }

    private List<GoodsInfo> getList(int position) {
        List<GoodsInfo> list = new ArrayList<>();
        GoodsInfo goodsInfo = null;
        switch (position) {
            case 0:
                for (int i = 0; i < shop1_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[0]);
                    goodsInfo.setImageUrl(shop1_pics[i]);
                    goodsInfo.setGoodsName(shop1_names[i]);
                    goodsInfo.setGoodsAmount(shop1_num[i]);
                    goodsInfo.setGoodsPrice(shop1_price[i]);
                    goodsInfo.setGoodsInfo(shop1_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop1_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
            case 1:
                for (int i = 0; i < shop2_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[1]);
                    goodsInfo.setImageUrl(shop2_pics[i]);
                    goodsInfo.setGoodsName(shop2_names[i]);
                    goodsInfo.setGoodsAmount(shop2_num[i]);
                    goodsInfo.setGoodsPrice(shop2_price[i]);
                    goodsInfo.setGoodsInfo(shop2_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop2_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
            case 2:
                for (int i = 0; i < shop3_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[2]);
                    goodsInfo.setImageUrl(shop3_pics[i]);
                    goodsInfo.setGoodsName(shop3_names[i]);
                    goodsInfo.setGoodsAmount(shop3_num[i]);
                    goodsInfo.setGoodsPrice(shop3_price[i]);
                    goodsInfo.setGoodsInfo(shop3_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop3_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
            case 3:
                for (int i = 0; i < shop4_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[3]);
                    goodsInfo.setImageUrl(shop4_pics[i]);
                    goodsInfo.setGoodsName(shop4_names[i]);
                    goodsInfo.setGoodsAmount(shop4_num[i]);
                    goodsInfo.setGoodsPrice(shop4_price[i]);
                    goodsInfo.setGoodsInfo(shop4_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop4_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
            case 4:
                for (int i = 0; i < shop5_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[4]);
                    goodsInfo.setImageUrl(shop5_pics[i]);
                    goodsInfo.setGoodsName(shop5_names[i]);
                    goodsInfo.setGoodsAmount(shop5_num[i]);
                    goodsInfo.setGoodsPrice(shop5_price[i]);
                    goodsInfo.setGoodsInfo(shop5_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop5_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
            case 5:
                for (int i = 0; i < shop6_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setShopName(shopNames[5]);
                    goodsInfo.setImageUrl(shop6_pics[i]);
                    goodsInfo.setGoodsName(shop6_names[i]);
                    goodsInfo.setGoodsAmount(shop6_num[i]);
                    goodsInfo.setGoodsPrice(shop6_price[i]);
                    goodsInfo.setGoodsInfo(shop6_names[i] + detail);
                    goodsInfo.setGoodsLevel(shop6_star[i] + "");
                    list.add(goodsInfo);
                }
                break;
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "one onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "one onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "one onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "one onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "one onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "one onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "one onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "one onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "one onStop");
    }
}
