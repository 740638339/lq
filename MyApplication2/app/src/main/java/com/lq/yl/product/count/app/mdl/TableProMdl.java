package com.lq.yl.product.count.app.mdl;

import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/13.
 */

public class TableProMdl extends Model{
    /**
     *
     */
    private String mTableTitle;         //桌位名称

    private String mProTotal = "0";     //售出总金额
    private String mProRevenue = "0";   //进价总金额
    private String mStartTime = "";    //桌位创建时间
    private String mEndTime = "";       //桌位结束时间
    private String mTabDate = "";       //日期
    private String mIsPay = "0";        //是否结账
    private String mOrderId = "";       //外键
    private List<ProductMdl> mProList;  //产品列表

    public String getTableTitle() {
        return mTableTitle;
    }

    public void setTableTitle(String mTableTitle) {
        this.mTableTitle = mTableTitle;
    }

    public String getProTotal() {
        return mProTotal;
    }

    public void setProTotal(String mProTotal) {
        this.mProTotal = mProTotal;
    }

    public String getProRevenue() {
        return mProRevenue;
    }

    public void setProRevenue(String mProRevenue) {
        this.mProRevenue = mProRevenue;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getTabDate() {
        return mTabDate;
    }

    public void setTabDate(String mTabTime) {
        this.mTabDate = mTabTime;
    }

    public String getIsPay() {
        return mIsPay;
    }

    public void setIsPay(String mIsPay) {
        this.mIsPay = mIsPay;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public List<ProductMdl> getProList() {
        return mProList;
    }

    public void setProList(List<ProductMdl> mProList) {
        this.mProList = mProList;
    }

}
