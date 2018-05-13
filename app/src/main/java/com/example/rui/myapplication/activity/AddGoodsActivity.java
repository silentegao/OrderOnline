package com.example.rui.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui.myapplication.R;
import com.example.rui.myapplication.bean.GoodsInfo;
import com.example.rui.myapplication.utils.MySpinner;
import com.example.rui.myapplication.utils.TakePhotoUtils;
import com.yalantis.ucrop.entity.LocalMedia;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @BindView(R.id.spinner_shop)
    MySpinner spinnerShop;
    List<String> lstSpinnerShop;

    Context context;

    Unbinder unbinder;
    public String filePath;
    String shopPicName;
    String goodsName;
    GoodsInfo goodsInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_add_goods);
        unbinder = ButterKnife.bind(this);
        initView();
        Log.i("---->", "onCreate");
    }

    void initView() {
        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhotoUtils.open(AddGoodsActivity.this, 1, new TakePhotoUtils.PhotoSelectinterface() {
                    @Override
                    public void selectSuccess(List<LocalMedia> result) {

                        for (LocalMedia item : result) {
                            item.getPath();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String dateStr = sdf.format(new Date(System.currentTimeMillis()));
                            shopPicName = dateStr;

                            filePath = Environment.getExternalStorageDirectory().
                                    getAbsolutePath() + "/" + dateStr + ".png";

                            item.getCompressPath();
                            Log.e("AddGoodsActivity", "r________" + item.getPath() + "++++++" + item.getCompressPath());
                            Log.e("AddGoodsActivity", "dateStr________" + "++++++" + dateStr);
                            Log.e("AddGoodsActivity", "filePath________" + "++++++" + filePath);
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(item.getPath());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            final Bitmap bitmap = BitmapFactory.decodeStream(fis);
                            ivPic.setImageBitmap(bitmap);
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    saveImg(bitmap);
                                }
                            }.start();

                        }
                    }
                });
            }
        });

        lstSpinnerShop = new ArrayList<>();
        lstSpinnerShop = Arrays.asList(getResources().getStringArray(R.array.add_shop_name));
        spinnerShop.setData(lstSpinnerShop);
        spinnerShop.setSelected("请选择");
        spinnerShop.setOnItemSelectedListener(new OnItemSelectedListenerShop());
        btnSubmit.setOnClickListener(new OnClickListenerSubmit());
    }

    String strShopName = "";

    /**
     * 店铺选择
     */
    class OnItemSelectedListenerShop implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            strShopName = lstSpinnerShop.get(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    class OnClickListenerSubmit implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            creatGoodsInfo();
        }
    }

    void creatGoodsInfo() {
        goodsName = edtName.getText().toString().trim();
        if (rangeCheck()) {
            return;
        }
        goodsInfos = DataSupport.where("goodsName = ? and status = 1", goodsName).findFirst(GoodsInfo.class);
        if (goodsInfos == null) {
            goodsInfos = new GoodsInfo();
            goodsInfos.setShopName(strShopName);
            goodsInfos.setImagePicName(shopPicName);
            goodsInfos.setGoodsName(goodsName);
            goodsInfos.setGoodsPrice(Long.parseLong(edtPrice.getText().toString().trim()));
            goodsInfos.setGoodsAmount(Integer.parseInt(edtAmount.getText().toString().trim()));
            goodsInfos.setGoodsLevel("4.5");
            goodsInfos.setGoodsInfo(edtDetail.getText().toString().trim());
            goodsInfos.setStatus(1);
            goodsInfos.setIsUpdate(1);
            goodsInfos.setCreateTime(new Date(System.currentTimeMillis()));
            goodsInfos.setModifyTime(new Date(System.currentTimeMillis()));
            goodsInfos.save();
        } else {
            if (isRegister(goodsName)) {
                Toast.makeText(this, "该商品名已存在，不可重复添加！", Toast.LENGTH_SHORT).show();
                return;
            }
            goodsInfos.setModifyTime(new Date(System.currentTimeMillis()));
        }
        Toast.makeText(this, "菜品添加成功", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    boolean rangeCheck() {
        if (TextUtils.isEmpty(shopPicName)) {
            Toast.makeText(this, "菜品图片不能能为空，请添加菜品图片", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(strShopName)) {
            Toast.makeText(this, "店铺名称不能为空，请选择店铺名称", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(goodsName)) {
            Toast.makeText(this, "菜品名称不能为空，请添加菜品名称", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edtPrice.getText().toString().trim())) {
            Toast.makeText(this, "菜品单价不能为空，请添加菜品单价", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edtAmount.getText().toString().trim())) {
            Toast.makeText(this, "菜品数量不能为空，请添加菜品数量", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edtDetail.getText().toString().trim())) {
            Toast.makeText(this, "菜品名称不能描述，请添加菜品描述", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 是否已经添加
     *
     * @param goodsName
     * @return
     */
    boolean isRegister(String goodsName) {
        List<GoodsInfo> userInfoTemp = DataSupport.where("status = 1").find(GoodsInfo.class);
        for (GoodsInfo item : userInfoTemp) {
            if (goodsName.equals(item.getGoodsName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //读取位图（图片）
    private Bitmap readImg() {
        File mfile = new File(filePath);
        Bitmap bm = null;
        if (mfile.exists()) {        //若该文件存在
            bm = BitmapFactory.decodeFile(filePath);
            ivPic.setImageBitmap(bm);
            Log.e("AddGoodsActivity", "bm" + "++++++" + bm);
        }
        return bm;
    }

    //保存图片到本地，下次直接读取
    private void saveImg(Bitmap mBitmap) {
        File f = new File(filePath);
        try {
            //如果文件不存在，则创建文件
            if (!f.exists()) {
                f.createNewFile();
            }
            //输出流
            FileOutputStream out = new FileOutputStream(f);
            /** mBitmap.compress 压缩图片
             *
             *  Bitmap.CompressFormat.PNG   图片的格式
             *   100  图片的质量（0-100）
             *   out  文件输出流
             */
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Log.e("AddGoodsActivity", "FileOutputStream" + "++++++" + filePath);
            out.flush();
            out.close();
//            Toast.makeText(this, f.getAbsolutePath().toString(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getImg(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            //从输入流中解码位图
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //保存位图
            ivPic.setImageBitmap(bitmap);
//            cutImg(uri);
            //关闭流
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
