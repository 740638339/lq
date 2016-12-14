package com.lq.yl.product.count.app.mdl;

import java.util.ArrayList;

/**
 * Created by wb-liuquan.e on 2016/11/9.
 */
public class OrderMdl extends Model{

    private String mCount;
    private String mActRevenueTotal = "0";

    private String mRevenueTotal = "0";
    private String mYear = "0";
    private String mMonth = "0";
    private String mDay = "0";
    private String mWeekDay = "0";
    private String mCreateDate = "";

    private ArrayList<TableProMdl> mTableList;

    public String getCount() {
        return mCount;
    }

    public void setCount(String mCount) {
        this.mCount = mCount;
    }

    public String getActRevenueTotal() {
        return mActRevenueTotal;
    }

    public void setActRevenueTotal(String mActRevenueTotal) {
        this.mActRevenueTotal = mActRevenueTotal;
    }

    public String getRevenueTotal() {
        return mRevenueTotal;
    }

    public void setRevenueTotal(String mRevenueTotal) {
        this.mRevenueTotal = mRevenueTotal;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String mCreateTime) {
        this.mCreateDate = mCreateTime;
    }

    public ArrayList<TableProMdl> getTableList() {
        return mTableList;
    }

    public void setTableList(ArrayList<TableProMdl> mTableList) {
        this.mTableList = mTableList;
    }


    public String getYear() {
        return mYear;
    }

    public void setYear(String mYear) {
        this.mYear = mYear;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String mDay) {
        this.mDay = mDay;
    }

    public String getWeekDay() {
        return mWeekDay;
    }

    public void setWeekDay(String mWeekDay) {
        this.mWeekDay = mWeekDay;
    }
}
