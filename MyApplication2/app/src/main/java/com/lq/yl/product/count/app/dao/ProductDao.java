package com.lq.yl.product.count.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lq.yl.product.count.app.helper.DBHelper;
import com.lq.yl.product.count.app.mdl.ProductMdl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wb-liuquan.e on 2016/10/24.
 */
public class ProductDao extends IDao{

    public final static String TABLE_PRODUCT_INFO = "TABLE_PRODUCT_INFO";
    public final static String CREATE_TABLE_PRODUCT_INFO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PRODUCT_INFO
            + " (ID VARCHAR PRIMARY KEY, "
            + "PRONAME VARCHAR, "
            + "PRO_ORG_PRICE VARCHAR,"
            + "PRO_SALE_PRICE VARCHAR,"
            + "PRONUM VARCHAR, "
            + "PRO_SALE_SUM VARCHAR, "
            + "ENTER_DATE DATETIME, "
            + "TABLE_ID VARCHAR, "
            + "PRO_PHOTO_URL VARCHAR); ";

    public ProductDao(Context context) {
        // helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        this.helper = new DBHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    //插入商品
    public void insert(ProductMdl mdl) {
        try {
            mdl.setID(UUID.randomUUID().toString());
            mdl.setIsNewItem(false);
            db.execSQL("INSERT INTO " + TABLE_PRODUCT_INFO + " (ID,PRONAME,PRO_ORG_PRICE,PRO_SALE_PRICE,PRONUM,PRO_SALE_SUM,ENTER_DATE,TABLE_ID,PRO_PHOTO_URL) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)", new String[]{mdl.getID(), mdl.getProName(), mdl.getProOrgPrice(),mdl.getProSalePrice(), mdl.getProNum(),mdl.getProSaleSum(), mdl.getEnterDate(),mdl.getTableId(), mdl.getProPhotoUrl()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Id删除商品
    public void del(String id) {
        if (id != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_PRODUCT_INFO + " WHERE ID = ?", new String[]{id});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //根据外键Id删除商品
    public void pkDel(String pkId) {
        if (pkId != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_PRODUCT_INFO + " WHERE TABLE_ID=?", new String[]{pkId});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //更新商品信息
    public void update(ProductMdl mdl) {
        mdl.setIsNewItem(false);
        try {
            db.execSQL("UPDATE " + TABLE_PRODUCT_INFO + " SET PRONAME=?,PRO_ORG_PRICE=?,PRO_SALE_PRICE=?,PRONUM=?,PRO_SALE_SUM=?,ENTER_DATE=?,TABLE_ID=?,PRO_PHOTO_URL=? " + " WHERE ID=? ",
                    new String[]{mdl.getProName(), mdl.getProOrgPrice(),mdl.getProSalePrice(), mdl.getProNum(),mdl.getProSaleSum(), mdl.getEnterDate(), mdl.getTableId(), mdl.getProPhotoUrl(), mdl.getID()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据id查询商品信息
    public ProductMdl queryMdlByID(String id) {
        ProductMdl mdl = null;
        if (id != null) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT_INFO + " WHERE ID=?", new String[]{id});
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        mdl = new ProductMdl();
                        mdl.setID(cursor.getString(cursor.getColumnIndex("ID")));
                        mdl.setProName(cursor.getString(cursor.getColumnIndex("PRONAME")));
                        mdl.setProNum(cursor.getString(cursor.getColumnIndex("PRONUM")));
                        mdl.setProSaleSum(cursor.getString(cursor.getColumnIndex("PRO_SALE_SUM=?")));
                        mdl.setProOrgPrice(cursor.getString(cursor.getColumnIndex("PRO_ORG_PRICE")));
                        mdl.setProSalePrice(cursor.getString(cursor.getColumnIndex("PRO_SALE_PRICE")));
                        mdl.setEnterDate(cursor.getString(cursor.getColumnIndex("ENTER_DATE")));
                        mdl.setTableId(cursor.getString(cursor.getColumnIndex("TABLE_ID")));
                        mdl.setProPhotoUrl(cursor.getString(cursor.getColumnIndex("PRO_PHOTO_URL")));
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mdl;
    }

    //查询所有商品信息数量
    public int getCount() {
        String sql = "SELECT * FROM " + TABLE_PRODUCT_INFO;
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //根据id商品信息数量
    public int QueryByIdCount(String id) {
        int count = 0;
        if (id != null) {
            String sql = "SELECT * FROM " + TABLE_PRODUCT_INFO + " WHERE ID=?";
            Cursor cursor = db.rawQuery(sql, new String[]{id});

            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public List<ProductMdl> productList() {
        List<ProductMdl> list = new ArrayList<ProductMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_PRODUCT_INFO;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        ProductMdl mdl = new ProductMdl();
                        if ((cursor.getString(7) == null || cursor.getString(7).equals(""))
                        &&(cursor.getString(4) == null || cursor.getString(4).equals("0") || cursor.getString(0).equals(""))){
                            mdl.setID(cursor.getString(0));
                            mdl.setProName(cursor.getString(1));
                            mdl.setProOrgPrice(cursor.getString(2));
                            mdl.setProSalePrice(cursor.getString(3));
                            mdl.setProNum(cursor.getString(4));
                            mdl.setProSaleSum(cursor.getString(5));
                            mdl.setEnterDate(cursor.getString(6));
                            mdl.setTableId(cursor.getString(7));
                            mdl.setProPhotoUrl(cursor.getString(8));
                            list.add(mdl);
                        }
                    }
                }
                cursor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<ProductMdl> pkListAll(String id) {
        List<ProductMdl> list = new ArrayList<ProductMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_PRODUCT_INFO + " WHERE TABLE_ID=?";

            Cursor cursor = db.rawQuery(sql, new String[]{id});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        ProductMdl mdl = new ProductMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setProName(cursor.getString(1));
                        mdl.setProOrgPrice(cursor.getString(2));
                        mdl.setProSalePrice(cursor.getString(3));
                        mdl.setProNum(cursor.getString(4));
                        mdl.setProSaleSum(cursor.getString(5));
                        mdl.setEnterDate(cursor.getString(6));
                        mdl.setTableId(cursor.getString(7));
                        mdl.setProPhotoUrl(cursor.getString(8));
                        list.add(mdl);
                    }
                }
                cursor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}

