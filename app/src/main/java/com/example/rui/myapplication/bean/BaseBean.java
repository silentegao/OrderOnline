package com.example.rui.myapplication.bean;


import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/1/23/023.
 */

public class BaseBean extends DataSupport {

    /**
     * 是否更新 0代表不更新，1代表更新
     */
    private Integer isUpdate;

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
//        if (isUpdate == 1) {
//            Doctor doctor = DataSupport.where("uid = ?", Cons.UID).findFirst(Doctor.class);
//            if (doctor.getIsHasUpdate() != 1) {
//                doctor.setIsHasUpdate(1);
//                doctor.save();
//            }
//        }
    }
}
