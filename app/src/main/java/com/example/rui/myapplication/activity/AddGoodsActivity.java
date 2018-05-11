package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rui.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddGoodsActivity extends Activity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_price)
    EditText edtPrice;
    @BindView(R.id.edt_amount)
    EditText edtAmount;
    @BindView(R.id.edt_detail)
    EditText edtDetail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    Context context;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        unbinder = ButterKnife.bind(this);
        initView();
        Log.i("---->", "onCreate");
    }

    void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
