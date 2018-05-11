package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.VerificationCode;
import com.example.rui.myapplication.bean.UserInfo;
import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.utils.MessageEvent;
import com.example.rui.myapplication.utils.UUIDUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_shimmer_mainname)
    TextView tvShimmerMainname;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_regphone)
    EditText edtRegphone;
    @BindView(R.id.edt_codes)
    EditText edtCodes;
    @BindView(R.id.showCode)
    ImageView showCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private String realCode;
    private SharedPreferences sp;
    UserInfo userInfo;
    List<UserInfo> LstUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        showCode = (ImageView) findViewById(R.id.showCode);
        //将验证码用图片的形式显示出来
        showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();

    }

    public void register(View view) {
        if (rangeCheck()) {
            return;
        }
        if (!edtCodes.getText().toString().toLowerCase().equals(realCode)) {
            Toast.makeText(RegisterActivity.this, "验证码错误，请重试！", Toast.LENGTH_SHORT).show();
            showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
            realCode = VerificationCode.getInstance().getCode().toLowerCase();
        } else {
//            userInfo = DataSupport.findFirst(UserInfo.class);
            String account = edtUsername.getText().toString();
            userInfo = DataSupport.where("userAccount = ? and status = 1", account).findFirst(UserInfo.class);
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setUid(UUIDUtil.get32UUID());
                userInfo.setUserAccount(edtUsername.getText().toString());
                userInfo.setUserPassword(edtPassword.getText().toString());
                userInfo.setUserPhone(edtRegphone.getText().toString());
                userInfo.setStatus(1);
                userInfo.setIsUpdate(1);
                userInfo.setCreateTime(new Date(System.currentTimeMillis()));
                userInfo.setModifyTime(new Date(System.currentTimeMillis()));
                userInfo.save();
            } else {
                if (isRegister(account)) {
                    Toast.makeText(this, "该账号已存在，不可重复注册！", Toast.LENGTH_SHORT).show();
                    return;
                }
                userInfo.setModifyTime(new Date(System.currentTimeMillis()));
            }


            EventBus.getDefault().post(new MessageEvent(Cons.REFRESH_LOGIN));
            Toast.makeText(this, "用户注册成功", Toast.LENGTH_SHORT).show();
            this.finish();
//            FormBody.Builder builder1 = new FormBody.Builder();
//            FormBody formBody = builder1.add("id", username.getText().toString())
//                    .add("pwd", password.getText().toString())
//                    .add("regphone", regphone.getText().toString()).build();
//
//            Request.Builder builder = new Request.Builder();
//            Request request = builder.url("http://" + getString(R.string.ip) + ":8080/hafu_project/signup_android")
//                    .post(formBody)
//                    .build();
//            exec(request);
        }
    }

    public void exitToHome(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void codeChange(View view) {
        showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();
    }

    boolean rangeCheck() {
        if (TextUtils.isEmpty(edtUsername.getText().toString())) {
            Toast.makeText(this, "用户名不能为空，请输入用户名", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            Toast.makeText(this, "用户密码不能为空，请输入用户密码", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edtRegphone.getText().toString())) {
            Toast.makeText(this, "手机号码格式不正确，请输入11位手机号", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!TextUtils.isEmpty(edtRegphone.getText().toString())) {
            if (edtRegphone.getText().toString().length() != 11) {
                Toast.makeText(this, "手机号码格式不正确，请输入11位手机号", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * 是否已经注册
     *
     * @param userAccount
     * @return
     */
    boolean isRegister(String userAccount) {
        List<UserInfo> userInfoTemp = DataSupport.where("status = 1").find(UserInfo.class);
        for (UserInfo item : userInfoTemp) {
            if (edtUsername.getText().toString().equals(item.getUserAccount())) {
                return true;
            }
//            Date date = DateTimeUtil.string2Date(item.getSignEndDate(), "yyyy-MM-dd");
//            if (date.getTime() > System.currentTimeMillis()) {
//                return true;
//            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
