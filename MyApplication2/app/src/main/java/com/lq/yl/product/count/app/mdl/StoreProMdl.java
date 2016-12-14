package com.lq.yl.product.count.app.mdl;


import java.util.ArrayList;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class StoreProMdl extends Model {

    private String mStoreName;

    private String mStoreDec;

    private String mCreateTime;
    private ArrayList<ProductMdl> proList;

    public ArrayList<ProductMdl> getProList() {
        return proList;
    }

    public void setProList(ArrayList<ProductMdl> proList) {
        this.proList = proList;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String mStoreName) {
        this.mStoreName = mStoreName;
    }

    public String getStoreDec() {
        return mStoreDec;
    }

    public void setStoreDec(String mStoreDec) {
        this.mStoreDec = mStoreDec;
    }

    public String getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public StoreProMdl() {

    }

}
