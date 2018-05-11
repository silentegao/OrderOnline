package com.example.rui.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.adapter.FragAdapter;
import com.example.rui.myapplication.fragment.OneFragment;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private FragAdapter adapter;

    private String getTitle;
    private int getPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        Intent intent = getIntent();
        if (intent != null){
            getTitle = intent.getStringExtra("TITLE");
            getPosition = intent.getIntExtra("POSITION",0);
        }

        viewPager = (ViewPager)findViewById(R.id.tab_fragment_container);
        fragmentList.add(OneFragment.newInstance(getTitle,getPosition));
        adapter = new FragAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

    }
}
