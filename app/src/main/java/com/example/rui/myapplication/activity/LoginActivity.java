package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.View.VerificationCode;
import com.example.rui.myapplication.bean.UserInfo;
import com.example.rui.myapplication.cons.Cons;
import com.example.rui.myapplication.cons.SPCon;
import com.example.rui.myapplication.database.DataUtil;
import com.example.rui.myapplication.utils.MessageEvent;
import com.example.rui.myapplication.utils.SPUtils;
import com.example.rui.myapplication.utils.UUIDUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.lang.Boolean.TRUE;

public class LoginActivity extends Activity {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.forgetPassword)
    Button forgetPassword;
    @BindView(R.id.tv_shimmer_mainname)
    TextView tvShimmerMainname;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_codes)
    EditText edtCodes;
    @BindView(R.id.showCode)
    ImageView showCode;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String realCode;
    private SharedPreferences sp;
    Context context;
    List<UserInfo> userInfoTemp;
    Unbinder unbinder;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Log.i("---->", "onCreate");
        Cons.ACCOUNT = "OrderOnlineDB";
        context = this;
        //权限申请
        AndPermission.with(context)
                .requestCode(101)
                .permission(Permission.STORAGE, Permission.CAMERA, Permission.LOCATION, Permission.PHONE)
                .rationale(new CamerRationaleListener())
                .callback(new CamerPermissionListener())
                .start();

        sp = getSharedPreferences("msg", MODE_PRIVATE);

        String user = sp.getString("username", "");
        String pwd = sp.getString("password", "");
        String tmp = sp.getString("isRemember", "");
        if (tmp.equals("TRUE"))
            cbRemember.setChecked(TRUE);
        edtUsername.setText(user);
        edtPassword.setText(pwd);
        //将验证码用图片的形式显示出来
        showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();
        initFile();
        creatAdminUserAccount();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("---->", "onStop");
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", edtUsername.getText().toString());
        editor.putString("isRemember", cbRemember.isChecked() ? "TRUE" : "FALSE");
        if (cbRemember.isChecked()) {
            editor.putString("password", edtPassword.getText().toString());
            editor.putString("isRemember", "TRUE");
        } else {
            editor.putString("password", "");
            editor.putString("isRemember", "FALSE");
        }
        editor.commit();
        Log.i("---->", "存储成功!");
    }

    public void login(View view) {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("username",username.getText().toString());
//        editor.putString("isLogin","TRUE");
//        editor.commit();
        if (rangeCheck()) {
            return;
        }
        if (!edtCodes.getText().toString().toLowerCase().equals(realCode)) {
            Toast.makeText(LoginActivity.this, "验证码错误，请重试！", Toast.LENGTH_SHORT).show();
            showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
            realCode = VerificationCode.getInstance().getCode().toLowerCase();
        } else {
//            FormBody.Builder builder1 = new FormBody.Builder();
//            FormBody formBody = builder1.add("id", edtUsername.getText().toString())
//                    .add("pwd", password.getText().toString()).build();
//
//            Request.Builder builder = new Request.Builder();
//            Request request = builder.url("http://" + getString(R.string.ip) + ":8080/hafu_project/login_android")
//                    .post(formBody)
//                    .build();
//            exec(request);
            String account = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            userInfoTemp = DataSupport.where("userAccount = ? and status = 1", account).find(UserInfo.class);
            if (userInfoTemp.size() == 0) {
                Toast.makeText(context, "该账号不存在，请更换账号或注册新账号!", Toast.LENGTH_SHORT).show();
                return;
            }
            for (UserInfo item : userInfoTemp) {
                if (account.equals(item.getUserAccount())) {
                    String userPassword = item.getUserPassword();
                    if (password.equals(userPassword)) {
                        String uuid = DataSupport.where("userAccount = ? and status = 1", account).findFirst(UserInfo.class).getUid();
                        SPUtils.put(context, SPCon.USERACCOUNT, account);
                        SPUtils.put(context, SPCon.USERPASSWORD, password);
                        SPUtils.put(context, SPCon.UUID, uuid);
                        Cons.UID = uuid;
                        Log.e("LoginActivity", uuid);
//                        Cons.userInfo = DataSupport.findFirst(UserInfo.class);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        this.finish();
                    } else {
                        Toast.makeText(context, "账号或密码错误，请重新输入!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "该账号不存在!", Toast.LENGTH_SHORT).show();
                }
            }
//            String md5File = MD5Util.getMd5ByFile(new File(Cons.TEMP_PATH + Cons.TEMP_DOWN_FILE_NAME));
//            Log.e("LoginActivity", "md5File=" + md5File);
//            saveJson(md5File);

        }
    }

    void creatAdminUserAccount() {
        userInfo = DataSupport.where("userAccount = ? and status = 1", "admin").findFirst(UserInfo.class);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUid(UUIDUtil.get32UUID());
            userInfo.setUserAccount("admin");
            userInfo.setUserPassword("admin");
            userInfo.setUserPhone("18795907606");
            userInfo.setStatus(1);
            userInfo.setIsUpdate(1);
            userInfo.setCreateTime(new Date(System.currentTimeMillis()));
            userInfo.setModifyTime(new Date(System.currentTimeMillis()));
            userInfo.save();
        }
    }

    /**
     * 初始化文件路径
     */
    void initFile() {

        File fileAccount = new File(Cons.BASE_PATH + "/" + Cons.ACCOUNT);
        if (!fileAccount.exists()) {
            fileAccount.mkdirs();
        }


        File fileDb = new File(Cons.BASE_PATH + "/" + Cons.ACCOUNT + "/db/");
        if (!fileDb.exists()) {
            fileDb.mkdirs();
        }
        Cons.TEMP_PATH = Cons.BASE_PATH + "/" + Cons.ACCOUNT + "/temp/";
        File fileDbTemp = new File(Cons.TEMP_PATH);
        if (!fileDbTemp.exists()) {
            fileDbTemp.mkdirs();
        }
        Cons.FILES_PATH = Cons.BASE_PATH + "/" + Cons.ACCOUNT + "/files/";
        File filefilesPath = new File(Cons.FILES_PATH);
        if (!filefilesPath.exists()) {
            filefilesPath.mkdirs();
        }

//        LitePalAttr lp = LitePalAttr.getInstance();
//        lp.setDbName(Cons.ACCOUNT);
//        String dbName = lp.getDbName();
//        Cons.DB_PATH = Cons.BASE_PATH + "/" + Cons.ACCOUNT + "/db/" + dbName;
//        lp.setStorage(Cons.ACCOUNT + "/db/");
//        Log.e("LoginActivity", "-------------:>>" + Cons.DB_PATH);
    }

    void saveJson(final String s) {
        DataUtil.getInstance().saveJson();
//        SPUtils.put(context, SPCon.IS_DOWN, true);
//        handler.sendEmptyMessage(100);
    }

//    public void exitToHome(View view) {
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//    }

    public void signup(View view) {
//        DataUtil.getInstance().saveJson();
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void codeChange(View view) {
        showCode.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();
    }

    /**
     * 显示申请权限弹出框
     */
    class CamerRationaleListener implements RationaleListener {

        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            AndPermission.rationaleDialog(context, rationale).show();
        }
    }

    /**
     * 用户是否同意申请的权限
     */
    class CamerPermissionListener implements PermissionListener {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。

        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 101) {

            }
        }
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
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        userInfo = DataSupport.findFirst(UserInfo.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (Cons.REFRESH_LOGIN.equals(event.getMsg())) {
//            userInfoTemp = DataSupport.where("userAccount = ? and status = 1", edt).find(UserInfo.class);
            Log.e("LoginActivity", "refresh");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return true;
//    }

}
