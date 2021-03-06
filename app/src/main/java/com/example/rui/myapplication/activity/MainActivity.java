package com.example.rui.myapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.adapter.FragAdapter;
import com.example.rui.myapplication.fragment.OneFragment;
import com.example.rui.myapplication.fragment.ShopFragment;
import com.example.rui.myapplication.fragment.ThreeFragment;
import com.example.rui.myapplication.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long mExitTime = 0;
    private static final String TAG = "life";

    @BindView(R.id.radio_group_button)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_button_home)
    RadioButton mRadioButtonHome;
    @BindView(R.id.radio_button_order)
    RadioButton mRadioBtnOrder;
    @BindView(R.id.radio_button_my)
    RadioButton mRadioBtnMy;
    @BindView(R.id.tab_fragment_container)
    ViewPager fragmentVp;


    private List<Fragment> mFragments;
    FragAdapter fragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        initData();
        initView();

    }

    private void initData() {
        ButterKnife.bind(this);
        mFragments = new ArrayList<>();
//        mFragments.add(new OneFragment());
        mFragments.add(ShopFragment.newInstance());
        mFragments.add(new TwoFragment());
        mFragments.add(new ThreeFragment());
        fragAdapter = new FragAdapter(getSupportFragmentManager(), mFragments);
        fragmentVp.setAdapter(fragAdapter);

    }

    private void initView() {
        initBottomTab();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void initBottomTab() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment mFragment = null;
                switch (checkedId) {
                    case R.id.radio_button_home:
                        //mFragment = mFragments.get(0);
                        fragmentVp.setCurrentItem(0);
                        break;
                    case R.id.radio_button_order:
//                        mFragment = mFragments.get(1);
                        fragmentVp.setCurrentItem(1);
                        break;
                    case R.id.radio_button_my:
//                        mFragment = mFragments.get(2);
                        fragmentVp.setCurrentItem(2);
                        break;
                }
//                if(mFragments!=null){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.tab_fragment_container,mFragment).commit();
//                }
            }
        });
        mRadioButtonHome.setChecked(true);

        fragmentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioButtonHome.setChecked(true);
                        break;
                    case 1:
                        mRadioBtnOrder.setChecked(true);
                        break;
                    case 2:
                        mRadioBtnMy.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出订餐", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
