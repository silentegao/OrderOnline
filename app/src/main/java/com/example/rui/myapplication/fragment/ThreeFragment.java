package com.example.rui.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.activity.AddGoodsActivity;
import com.example.rui.myapplication.activity.LoginActivity;
import com.example.rui.myapplication.bean.UserInfo;
import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.cons.SPCon;
import com.example.rui.myapplication.utils.SPUtils;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/28 0028.
 */

public class ThreeFragment extends Fragment {
    private static final String TAG = "life";

    @BindView(R.id.user)
    TextView user;
    @BindView(R.id.tv_userAccount)
    TextView tvUserAccount;
    @BindView(R.id.tv_userPassword)
    TextView tvUserPassword;
    @BindView(R.id.tv_userPhone)
    TextView tvUserPhone;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.tv_uuid)
    TextView tvUuid;
    @BindView(R.id.ll_is_admin)
    LinearLayout llIsAdmin;
    @BindView(R.id.tv_contact)
    TextView tvContact;

    Unbinder unbinder;
    UserInfo userInfo;
    String userAccount;
    String userPassword;
    String userUserPhone;

    private String phone = "18795907606";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fragment, container, false);
        Log.d(TAG, "three onCreateView");
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    void initView() {
        userAccount = (String) SPUtils.get(getContext(), SPCon.USERACCOUNT, "");
        if ("admin".equals(userAccount)) {
            llIsAdmin.setVisibility(View.VISIBLE);
            llIsAdmin.setOnClickListener(new OnClickListenerAddGoods());
        } else {
            llIsAdmin.setVisibility(View.GONE);
        }
        userInfo = DataSupport.where("userAccount = ? and status = 1", userAccount).findFirst(UserInfo.class);
        if (userInfo != null) {
            tvUserAccount.setText(userInfo.getUserAccount());
            tvUserPassword.setText(userInfo.getUserPassword());
            tvUserPhone.setText(userInfo.getUserPhone());
        }
        btnExit.setOnClickListener(new OnClickListenerExit());
        tvUuid.setText(Cons.UID);

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    class OnClickListenerExit implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    // 添加菜谱
    class OnClickListenerAddGoods implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddGoodsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "three onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "three onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "three onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "three onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "three onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "three onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "three onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "three onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "three onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "three onStop");
    }
}
