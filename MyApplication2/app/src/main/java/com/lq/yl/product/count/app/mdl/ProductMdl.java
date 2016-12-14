package com.lq.yl.product.count.app.mdl;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class ProductMdl extends Model{

    private String proName;

    private String proOrgPrice;

    private String proSalePrice;

    private String enterDate;

    private String proNum = "0";

    private String proSaleSum = "0";

    private String proPhotoUrl;

    private boolean isNewItem = false;

    private String tableId;
    public ProductMdl() {
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProOrgPrice() {
        return proOrgPrice;
    }

    public void setProOrgPrice(String proPrice) {
        this.proOrgPrice = proPrice;
    }

    public String getProSalePrice() {
        return proSalePrice;
    }

    public void setProSalePrice(String proSalePrice) {
        this.proSalePrice = proSalePrice;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }
    public String getProPhotoUrl() {
        return proPhotoUrl;
    }

    public String getProSaleSum() {
        return proSaleSum;
    }

    public void setProSaleSum(String proSaleSum) {
        this.proSaleSum = proSaleSum;
    }

    public void setProPhotoUrl(String proPhotoUrl) {
        this.proPhotoUrl = proPhotoUrl;
    }

    public boolean isNewItem() {
        return isNewItem;
    }

    public void setIsNewItem(boolean isNewItem) {
        this.isNewItem = isNewItem;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
