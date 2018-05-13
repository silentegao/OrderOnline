package com.example.rui.myapplication.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.rui.myapplication.R;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.List;

public class TakePhotoUtils {

    public interface PhotoSelectinterface {
        public void selectSuccess(List<LocalMedia> result);
    }

    private static FunctionConfig initConfig(Context context, List<LocalMedia> selectMedia, int maxSelect) {
        FunctionConfig config = new FunctionConfig();

        config.setType(LocalMediaLoader.TYPE_IMAGE);
        config.setCompress(true);   // 是否压缩
        config.setMaxSelectNum(maxSelect);
        config.setSelectMode(FunctionConfig.MODE_MULTIPLE);    // 是否多选
        config.setShowCamera(true); //  是否显示相机
        config.setEnablePreview(true);  // 是否预览
        config.setCompressFlag(1);  // 1系统自带压缩,2鲁班压缩
        if (selectMedia != null) {
            config.setSelectMedia(selectMedia);
        }
        config.setThemeStyle(ContextCompat.getColor(context, R.color.bar_grey));

        return config;
    }

    public static void open(Context context, int maxSelect, final PhotoSelectinterface photoSelectinterface) {
        // 初始化参数配置,在启动相册
        PictureConfig.init(initConfig(context, null, maxSelect));
        // 启动相册并设置回调函数
        PictureConfig.getPictureConfig().openPhoto(context, new PictureConfig.OnSelectResultCallback() {
            @Override
            public void onSelectSuccess(List<LocalMedia> list) {
                photoSelectinterface.selectSuccess(list);
            }
        });
    }

    public static void open(Context context, List<LocalMedia> selectMedia, final PhotoSelectinterface photoSelectinterface) {
        // 初始化参数配置,在启动相册
        PictureConfig.init(initConfig(context, selectMedia, 4));
        // 启动相册并设置回调函数
        PictureConfig.getPictureConfig().openPhoto(context, new PictureConfig.OnSelectResultCallback() {
            @Override
            public void onSelectSuccess(List<LocalMedia> list) {
                photoSelectinterface.selectSuccess(list);
            }
        });
    }


}
