package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rui.myapplication.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.rui.myapplication.R.id.iv_pic;
import static com.example.rui.myapplication.R.id.tv_name;

public class OrderListDetailActivity extends Activity {
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(iv_pic)
    ImageView ivPic;

    @BindView(tv_name)
    TextView tvName;

    @BindView(R.id.tv_redu)
    TextView tvRedu;

    @BindView(R.id.rb_star)
    RatingBar rbStar;

    @BindView(R.id.tv_price2)
    TextView tvPrice2;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_price_lab)
    TextView tvPriceLab;

    @BindView(R.id.tv_num)
    TextView tvNum;

    @BindView(R.id.tv_num_lab)
    TextView tvNumLab;

    @BindView(R.id.tv_zongjia)
    TextView tvZongjia;

    @BindView(R.id.tv_zongjia2)
    TextView tvZongjia2;

    @BindView(R.id.tv_choose)
    TextView tvChoose;

    @BindView(R.id.tv_seatnum)
    TextView tvSeatnum;

    @BindView(R.id.tv_dingdan)
    TextView tvDingdan;

    @BindView(R.id.tv_dingdanNum)
    TextView tvDingdanNum;

    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @BindView(R.id.tv_shijian2)
    TextView tvShijian2;

    Context context;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_orderlist_detail);
        unbinder = ButterKnife.bind(this);
        initView();
        Log.i("---->", "onCreate");
    }

    void initView() {
        int imageUrl = getIntent().getIntExtra("ImageUrl", 0);
        String goodsName = getIntent().getStringExtra("GoodsTypeName");
        String imagePicName = getIntent().getStringExtra("ImagePicName");
        int goodsAmount = getIntent().getIntExtra("GoodsAmount", 0);
        String goodsPrice = String.valueOf(getIntent().getLongExtra("GoodsPrice", 0));
        String seatNo = getIntent().getStringExtra("SeatNo");
        String createTime = getIntent().getStringExtra("CreateTime");
        int id = getIntent().getIntExtra("Id", 0);

        if (TextUtils.isEmpty(imagePicName)) {
            ivPic.setImageResource(imageUrl);
        } else {
            String filePath = Environment.getExternalStorageDirectory().
                    getAbsolutePath() + "/" + imagePicName + ".png";
            File mfile = new File(filePath);
            Bitmap bm = null;
            if (mfile.exists()) {        //若该文件存在
                bm = BitmapFactory.decodeFile(filePath);
                ivPic.setImageBitmap(bm);
                Log.e("AddGoodsActivity", "bm" + "++++++" + bm);
            }
        }
//        ivPic.setImageResource(imageUrl);

        tvName.setText(goodsName);
        tvSeatnum.setText(seatNo);
        tvDingdanNum.setText(id + "号");
        tvShijian2.setText(createTime);
        tvNum.setText("x" + goodsAmount);
        tvZongjia2.setText(goodsPrice + " 元");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
