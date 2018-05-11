package com.example.rui.myapplication.utils;

/**
 * Created by jingcheng on 2018/2/27.
 */

public class MessageEvent {
    private String type;
    private int position;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    String msg;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    String imgName;

    boolean isHidle;

    public MessageEvent(String msg, String imgName) {
        this.imgName = imgName;
        this.msg = msg;
    }

    public MessageEvent(String msg, boolean isHidle) {
        this.isHidle = isHidle;
        this.msg = msg;
    }

    public boolean isHidle() {
        return isHidle;
    }

}
