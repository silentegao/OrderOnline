

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
 * 菜品表
 */
public class GoodsInfo extends BaseBean {

    /**
     * id
     */
    private Integer id;
    /**
     * 标题--商品名
     */
    private String goodsName;
    /**
     * 商品类型id
     */
    private Integer goodsType;
    /**
     * 商品类型名称
     */
    private String goodsTypeName;
    /**
     * 商品详情介绍
     */
    private String goodsInfo;
    /**
     * 商品图片
     */
    private Integer imageUrl;
    /**
     * 商品价格
     */
    private long goodsPrice;
    /**
     * 商品数量
     */
    private Integer goodsAmount;
    /**
     * 商品热度
     */
    private String goodsLevel;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(String goodsLevel) {
        this.goodsLevel = goodsLevel;
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

