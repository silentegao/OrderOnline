

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
 * 订单表
 */
public class OrdersInfo extends BaseBean {

    /**
     * id
     */
    private Integer id;
    /**
     * 店铺名
     */
    private String shopName;
    /**
     * 订单id号
     */
    private String orderUid;
    /**
     * 下单人id
     */
    private String orderUserUid;
    /**
     * 下单人
     */
    private String orderUserName;
    /**
     * 下单人手机号
     */
    private String orderUserPhone;
    /**
     * 商品类型id
     */
    private Integer goodsType;
    /**
     * 商品类型名称
     */
    private String goodsTypeName;
    /**
     * 商品图片
     */
    private Integer imageUrl;
    /**
     * 商品图片路径
     */
    private String imagePicName;
    /**
     * 商品价格
     */
    private long goodsPrice;
    /**
     * 商品数量
     */
    private Integer goodsAmount;
    /**
     * 座位号
     */
    private String seatNo;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getOrderUserUid() {
        return orderUserUid;
    }

    public void setOrderUserUid(String orderUserUid) {
        this.orderUserUid = orderUserUid;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderUserPhone() {
        return orderUserPhone;
    }

    public void setOrderUserPhone(String orderUserPhone) {
        this.orderUserPhone = orderUserPhone;
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

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePicName() {
        return imagePicName;
    }

    public void setImagePicName(String imagePicName) {
        this.imagePicName = imagePicName;
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

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
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

