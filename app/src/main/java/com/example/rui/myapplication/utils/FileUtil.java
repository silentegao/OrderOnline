package com.example.rui.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }

    public static String getFile(String filename) {
        ByteArrayOutputStream outputStream = null;
        FileInputStream fis = null;
        try {
            outputStream = new ByteArrayOutputStream();
            File file = new File(filename);

            fis = new FileInputStream(file);
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = fis.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                fis.close();
            } catch (IOException e) {
            }
        }
        return new String(outputStream.toByteArray());
    }

    /**
     * 删除临时文件
     *
     * @param path 临时文件的路径
     */
    public static void deleteTempFiles(String path) {
        File dirFile = new File(path);
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                LogUtil.d(FileUtil.class, files[i].getName());
                files[i].delete();
            }
        }
    }

    public static void saveBitMap(Bitmap bitmap, String path) {

        File avaterFile = new File(path);//设置文件名称
        if (avaterFile.exists()) {
            avaterFile.delete();
        }
        try {
            avaterFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存字符串到文件
     *
     * @param fileString
     * @param path
     */
    public static void saveStringToFile(String fileString, String path) {
        File avaterFile = new File(path);//设置文件名称
        if (avaterFile.exists()) {
            avaterFile.delete();
        }
        try {
            avaterFile.createNewFile();
            FileOutputStream writerStream = new FileOutputStream(avaterFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
            writer.write(fileString);
            writer.close();

//            FileOutputStream outStream = new FileOutputStream(avaterFile);
//            outStream.write(fileString.getBytes());
//            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
