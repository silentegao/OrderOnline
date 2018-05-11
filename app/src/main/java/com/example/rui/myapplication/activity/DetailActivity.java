package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.GridSpacingItemDecoration;
import com.example.rui.myapplication.adapter.SeatListAdapter;
import com.example.rui.myapplication.adapter.baseadapter.BaseRecycleAdapter;
import com.example.rui.myapplication.bean.OrdersInfo;
import com.example.rui.myapplication.bean.UserInfo;
import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.cons.SPCon;
import com.example.rui.myapplication.utils.DensityUtils;
import com.example.rui.myapplication.utils.SPUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  下单 选座
 */
public class DetailActivity extends Activity {

    private ImageView ivPic;
    private TextView tvName;
    private RatingBar rbStar;
    private TextView tvPrice;
    private TextView tvLast;
    private EditText etNum;
    private TextView tvDetail;
    private Button btnSubmit;
    private Button tvChoose;
    private TextView tvSeatNum;

    private int getGoodsId;
    private int getPic;
    private String getName;
    private float getStar;
    private long getPrice;
    private int getLast;
    private String getDetail;

    private int selectNumber = 1;
    int[] imgIDs = {R.drawable.star, R.drawable.pizza, R.drawable.ha};

    private String seat = "A2";
    private OrdersInfo ordersInfo;
    private UserInfo userInfo;

    private String userAccount = "";
    private String userUID = "";
    private String userPhone = "";

    private PopupWindow ppwSeat;
    private View vmSeat;
    private RecyclerView recyclerView;
    private SeatListAdapter adapter;
    private GridLayoutManager layoutManager;
    private List<String> seatList = new ArrayList<>();
    private int layoutIds = R.layout.item_seat;

    public static void actionStart(Context context,int goodsId,
                                   int pic,String name,float star,
                                   long price,int number,String detail){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra("GOODSID",goodsId);
        intent.putExtra("PIC",pic);
        intent.putExtra("NAME",name);
        intent.putExtra("STAR",star);
        intent.putExtra("PRICE",price);
        intent.putExtra("NUMBER",number);
        intent.putExtra("DETAIL",detail);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null){
            getGoodsId = intent.getIntExtra("GOODSID",0);
            getPic = intent.getIntExtra("PIC",R.mipmap.ic_default);
            getName = intent.getStringExtra("NAME");
            getStar = intent.getFloatExtra("STAR",0.0f);
            getPrice = intent.getLongExtra("PRICE",0);
            getLast = intent.getIntExtra("NUMBER",0);
            getDetail = intent.getStringExtra("DETAIL");
        }

        tvChoose = (Button)findViewById(R.id.tv_choose);
        tvSeatNum = (TextView)findViewById(R.id.tv_seatnum);
        ivPic = (ImageView)findViewById(R.id.iv_pic);
        tvName = (TextView)findViewById(R.id.tv_name);
        rbStar = (RatingBar)findViewById(R.id.rb_star);
        tvPrice = (TextView)findViewById(R.id.tv_price);
        tvLast = (TextView)findViewById(R.id.tv_last_num);
        etNum = (EditText)findViewById(R.id.et_num);
        tvDetail = (TextView)findViewById(R.id.tv_detail);
        btnSubmit = (Button)findViewById(R.id.btn_submit);

        etNum.setSelection(etNum.length());
        ivPic.setImageResource(getPic);

        if (TextUtils.isEmpty(getName) == false){
            tvName.setText(getName);
        }

        tvPrice.setText(String.valueOf(getPrice));
        rbStar.setRating(getStar);
        tvLast.setText(String.valueOf(getLast));

        if (TextUtils.isEmpty(getDetail) == false){
            tvDetail.setText("\t\t"+getDetail);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etNum.getText().toString().trim();
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(DetailActivity.this, "请填写数量", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        selectNumber = Integer.parseInt(number);
                        if (selectNumber > getLast){
                            Toast.makeText(DetailActivity.this, "数量过多", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(tvSeatNum.getText().toString().trim())){
                            Toast.makeText(DetailActivity.this, "请选择座位", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        save();
                    }catch (Exception e){}
                }
            }
        });

        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWhere();
            }
        });

        initUserInfo();

        initPopWindow();
    }

    private void initPopWindow() {

        String[] seats = {"A1","A2","A3","A4","A5","A6","A7","A8","A9","A10",
                "B1","B2","B3","B4","B5","B6","B7","B8","B9","B10",
                "C1","C2","C3","C4","C5","C6","C7","C8","C9","C10"};
        for (int i = 0; i < seats.length; i++) {
            seatList.add(seats[i]);
        }

        vmSeat = LayoutInflater.from(this).inflate(R.layout.pop_seat,null);
        recyclerView = (RecyclerView)vmSeat.findViewById(R.id.rv_seats);

        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtils.dp2px(this, 0), DensityUtils.dp2px(this, 8)));

        adapter = new SeatListAdapter(this,seatList,layoutIds);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                seat = seatList.get(position);
                tvSeatNum.setText(seat);
                ppwSeat.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);

        ppwSeat = createPopWindow(vmSeat);

    }

    private PopupWindow createPopWindow(View view){
        PopupWindow ppw = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new ColorDrawable(0xb0000000));

        return ppw;
    }

    private void initUserInfo(){
        String acc = (String) SPUtils.get(this, SPCon.USERACCOUNT, "");
        userInfo = DataSupport.where("userAccount = ? and status = 1", acc).findFirst(UserInfo.class);
        if (userInfo != null) {
            userAccount = userInfo.getUserAccount();
            userPhone = userInfo.getUserPhone();
        }
    }

    private void chooseWhere(){
        ppwSeat.setFocusable(true);
        ppwSeat.showAtLocation(btnSubmit, Gravity.CENTER,0,0);
    }

    private void save(){
        ordersInfo = new OrdersInfo();

        ordersInfo.setOrderUserName(userAccount);
        ordersInfo.setOrderUserUid(Cons.UID);
        ordersInfo.setOrderUserPhone(userPhone);

        ordersInfo.setGoodsType(getGoodsId);
        ordersInfo.setGoodsTypeName(getName);
        ordersInfo.setImageUrl(getPic);
        ordersInfo.setGoodsPrice(getPrice * selectNumber);
        ordersInfo.setGoodsAmount(selectNumber);
        ordersInfo.setSeatNo(seat);
        ordersInfo.setStatus(0);

        ordersInfo.setCreateTime(new Date(System.currentTimeMillis()));
        ordersInfo.setModifyTime(new Date(System.currentTimeMillis()));

        ordersInfo.save();

        Toast.makeText(this, "订单已生成,请在我的订单列表中查看!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
