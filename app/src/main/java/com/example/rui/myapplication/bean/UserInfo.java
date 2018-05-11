

/*
 * Powered By [gc-framework]
 * Since 2017 - 2018
 */


package com.example.rui.myapplication.bean;


/**
 * @author
 * @version 1.0
 * @since 1.0
 */

import java.util.Date;

/**
 * 用户表 账号密码
 */
public class UserInfo extends BaseBean {

    /**
     * id
     */

    private Integer id;
    /**
     * 用户uid
     */
    private String uid;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 手机号码
     */
    private String userPhone;
    /**
     * 状态：1使用   0未使用
     */
    private Integer status;
    /**
     * 创建时间       db_column: create_time
     */
    private Date createTime;
    /**
     * 修改时间       db_column: modify_time
     */
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

