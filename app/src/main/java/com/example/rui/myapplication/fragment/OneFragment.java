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
import com.example.rui.myapplication.bean.ShopsBean;
import com.example.rui.myapplication.utils.DensityUtils;

import org.litepal.crud.DataSupport;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.lang.Boolean.TRUE;

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
    private int[] ids = {R.id.tv_hots,R.id.tv_hot0,R.id.tv_hot1,R.id.tv_hot2};

    GridLayoutManager gridLayoutManager;
    GoodsRecyclerviewAdapter goodsRecyclerviewAdapter;
    Unbinder unbinder;
    GoodsInfo goodsInfo;
    List<GoodsInfo> lstGoodsInfo;
    private List<GoodsInfo> sourceList = new ArrayList<>();
    private String getTitle;
    private int getPosition;


    private String detail = "很好吃,特别优惠,满100送50,快来订购吧";

    private int[] shop1_pics = {R.mipmap.shop1_1,R.mipmap.shop1_2,R.mipmap.shop1_3,R.mipmap.shop1_4};
    private String[] shop1_names = {"蒜蓉蒸金针菇","豆渣饼烧杂鱼","鲜藤椒鱼","口水鸡"};
    private float[] shop1_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop1_price = {14,38,30,23};
    private int[] shop1_num = {33,123,60,34};

    private int[] shop2_pics = {R.mipmap.shop2_1,R.mipmap.shop2_2,R.mipmap.shop2_3,R.mipmap.shop2_4};
    private String[] shop2_names = {"榴莲芝士条传统小装6","草莓果饮买一赠一","9寸传统超级棒约翰（夜宵）","缤纷烤翅拼盘"};
    private float[] shop2_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop2_price = {32,44,76,52};
    private int[] shop2_num = {33,123,60,34};

    private int[] shop3_pics = {R.mipmap.shop3_1,R.mipmap.shop3_2,R.mipmap.shop3_3,R.mipmap.shop3_4};
    private String[] shop3_names = {"纯手工自制羊肉串","金针菇","五花肉（原味）","热狗"};
    private float[] shop3_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop3_price = {4,3,3,2};
    private int[] shop3_num = {33,123,60,34};

    private int[] shop4_pics = {R.mipmap.shop4_1,R.mipmap.shop4_2,R.mipmap.shop4_3,R.mipmap.shop4_4};
    private String[] shop4_names = {"招牌土鸭","香辣蟹钳","香辣虾","土鸭掌"};
    private float[] shop4_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop4_price = {38,238,40,77};
    private int[] shop4_num = {33,123,60,34};

    private int[] shop5_pics = {R.mipmap.shop5_1,R.mipmap.shop5_2,R.mipmap.shop5_3,R.mipmap.shop5_4};
    private String[] shop5_names = {"大号韭菜粉丝盒子","喷油咸鸭蛋","手工豇豆饼","皮蛋瘦肉粥（一品）"};
    private float[] shop5_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop5_price = {31,38,420,17};
    private int[] shop5_num = {33,123,60,34};

    private int[] shop6_pics = {R.mipmap.shop6_1,R.mipmap.shop6_2,R.mipmap.shop6_3,R.mipmap.shop6_4};
    private String[] shop6_names = {"超级大鸡排","鸡翅包饭","双拼奶茶","大满贯"};
    private float[] shop6_star = {4.1f,3.4f,5.0f,4.3f};
    private int[] shop6_price = {11,65,43,77};
    private int[] shop6_num = {33,123,60,34};

    public static OneFragment newInstance(String title,int n) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString("TITLE",title);
        args.putInt("POSITION",n);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "one onCreate");
        if (getArguments() != null){
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
            tvHots[i] = (TextView)view.findViewById(ids[i]);
            tvHots[i].setOnClickListener(new MyHotOnClickListener(i));
        }

        initView();
        return view;
    }

    class MyHotOnClickListener implements View.OnClickListener{

        private int position;
        public MyHotOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            List<GoodsInfo> list = new ArrayList<>();
            if (position == 0){
                etSearch.setText("");
            }
            for (GoodsInfo bean :
                    sourceList) {
                if (position == 0){
                    list.add(bean);
                }else if (position == 1){
                    if (Float.parseFloat(bean.getGoodsLevel()) == 5.0f){
                        list.add(bean);
                    }
                }else if (position == 2){
                    if (bean.getGoodsAmount() >= 50){
                        list.add(bean);
                    }
                }else if (position == 3){
                    if (bean.getGoodsPrice() > 25){
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
                    if (bean.getGoodsName().contains(key)){
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
        List<GoodsInfo> list = getList(getPosition);
        lstGoodsInfo.clear();
        lstGoodsInfo.addAll(list);
        sourceList.clear();
        sourceList.addAll(list);
//        List<Map<String, Object>> lists = new ArrayList<>();
//        int[] imgIDs = {R.drawable.star, R.drawable.pizza, R.drawable.ha};
//        String[] titles = {"星巴克", "必胜客", "哈根达斯"};
//        float[] ratings = {4.5f, 5f, 4f};
//        int[] eva_per_months = {39, 42, 68};
//        int[] number_per_months = {60, 79, 75};
//        String[] distances = {"2.43公里", "700米", "3.6公里"};
//        int[] min_prices = {30, 45, 40};
//        int[] extra_prices = {5, 3, 10};
//        Boolean[] zhuans = {TRUE, Boolean.FALSE, TRUE};
//        Boolean[] sus = {TRUE, Boolean.FALSE, Boolean.FALSE};
//        Boolean[] piao = {TRUE, Boolean.FALSE, TRUE};
//        Boolean[] joinMans = {TRUE, TRUE, Boolean.FALSE};
//        int[] man_totals = {20, 30, 0};
//        int[] man_salls = {5, 8, 0};
//        int[] dis = {10, 0, 20};
//
//        lstGoodsInfo.clear();
//
//        GoodsInfo goodsInfo1 = new GoodsInfo();
//        goodsInfo1.setImageUrl(0);
//        goodsInfo1.setGoodsName("星巴克");
//        goodsInfo1.setGoodsAmount(22);
//        goodsInfo1.setGoodsPrice(101);
//        goodsInfo1.setGoodsInfo("星巴克（Starbucks）是美国一家连锁咖啡公司的名称，1971年成立，是全球最大的咖啡连锁店，其总部坐落美国华盛顿州西雅图市。");
//        goodsInfo1.setGoodsLevel("4.5");
//        lstGoodsInfo.add(goodsInfo1);
//        sourceList.add(goodsInfo1);
//        GoodsInfo goodsInfo11 = new GoodsInfo();
//        goodsInfo11.setImageUrl(1);
//        goodsInfo11.setGoodsName("必胜客");
//        goodsInfo11.setGoodsAmount(122);
//        goodsInfo11.setGoodsPrice(77);
//        goodsInfo11.setGoodsInfo("必胜客是比萨专卖连锁企业之一，由法兰克·卡尼和丹·卡尼两兄弟在1958年，凭着由母亲借来的600美元于美国堪萨斯州威奇托创立首间必胜客餐厅。");
//        goodsInfo11.setGoodsLevel("3.0");
//        lstGoodsInfo.add(goodsInfo11);
//        sourceList.add(goodsInfo11);
//        GoodsInfo goodsInfo111 = new GoodsInfo();
//        goodsInfo111.setImageUrl(2);
//        goodsInfo111.setGoodsName("哈根达斯");
//        goodsInfo111.setGoodsAmount(52);
//        goodsInfo111.setGoodsPrice(67);
//        goodsInfo111.setGoodsInfo("哈根达斯（Häagen-Dazs）原为美国冰激凌品牌，1921年由鲁本·马特斯研制成功，1962在美国纽约上市。");
//        goodsInfo111.setGoodsLevel("4.0");
//        lstGoodsInfo.add(goodsInfo111);
//        sourceList.add(goodsInfo111);

        for (int i = 0; i < lstGoodsInfo.size(); i++) {
//            goodsInfo = DataSupport.where("status = 1").findFirst(GoodsInfo.class);
            goodsInfo = new GoodsInfo();
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

    private List<GoodsInfo> getList(int position){
        List<GoodsInfo> list = new ArrayList<>();
        GoodsInfo goodsInfo = null;
        switch (position){
            case 0:
                for (int i = 0; i < shop1_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop1_pics[i]);
                    goodsInfo.setGoodsName(shop1_names[i]);
                    goodsInfo.setGoodsAmount(shop1_num[i]);
                    goodsInfo.setGoodsPrice(shop1_price[i]);
                    goodsInfo.setGoodsInfo(shop1_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop1_star[i]+"");
                    list.add(goodsInfo);
                }
                break;
            case 1:
                for (int i = 0; i < shop2_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop2_pics[i]);
                    goodsInfo.setGoodsName(shop2_names[i]);
                    goodsInfo.setGoodsAmount(shop2_num[i]);
                    goodsInfo.setGoodsPrice(shop2_price[i]);
                    goodsInfo.setGoodsInfo(shop2_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop2_star[i]+"");
                    list.add(goodsInfo);
                }
                break;
            case 2:
                for (int i = 0; i < shop3_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop3_pics[i]);
                    goodsInfo.setGoodsName(shop3_names[i]);
                    goodsInfo.setGoodsAmount(shop3_num[i]);
                    goodsInfo.setGoodsPrice(shop3_price[i]);
                    goodsInfo.setGoodsInfo(shop3_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop3_star[i]+"");
                    list.add(goodsInfo);
                }
                break;
            case 3:
                for (int i = 0; i < shop4_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop4_pics[i]);
                    goodsInfo.setGoodsName(shop4_names[i]);
                    goodsInfo.setGoodsAmount(shop4_num[i]);
                    goodsInfo.setGoodsPrice(shop4_price[i]);
                    goodsInfo.setGoodsInfo(shop4_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop4_star[i]+"");
                    list.add(goodsInfo);
                }
                break;
            case 4:
                for (int i = 0; i < shop5_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop5_pics[i]);
                    goodsInfo.setGoodsName(shop5_names[i]);
                    goodsInfo.setGoodsAmount(shop5_num[i]);
                    goodsInfo.setGoodsPrice(shop5_price[i]);
                    goodsInfo.setGoodsInfo(shop5_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop5_star[i]+"");
                    list.add(goodsInfo);
                }
                break;
            case 5:
                for (int i = 0; i < shop6_pics.length; i++) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setImageUrl(shop6_pics[i]);
                    goodsInfo.setGoodsName(shop6_names[i]);
                    goodsInfo.setGoodsAmount(shop6_num[i]);
                    goodsInfo.setGoodsPrice(shop6_price[i]);
                    goodsInfo.setGoodsInfo(shop6_names[i]+detail);
                    goodsInfo.setGoodsLevel(shop6_star[i]+"");
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
