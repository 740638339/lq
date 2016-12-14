package com.lq.yl.product.count.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lq.yl.product.count.app.helper.DBHelper;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wb-liuquan.e on 2016/10/24.
 */
public class TabProDao extends IDao{
    public final static String TABLE_TAB_PRO = "TABLE_TAB_PRO";
    public final static String CREATE_TABLE_TAB_PRO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TAB_PRO
            + " (ID VARCHAR PRIMARY KEY, "
            + "TABEL_TITLE VARCHAR, "
            + "PRO_TOTAL VARCHAR, "
            + "PRO_REVENUE VARCHAR, "
            + "IS_PAY VARCHAR,"
            + "CREATE_TIME VARCHAR,"
            + "END_TIME VARCHAR,"
            + "TAB_DATE VARCHAR,"
            + "ORDER_ID VARCHAR); ";

    public TabProDao(Context context) {
        this.mContext = context;
        this.helper = new DBHelper(context);
        db = this.helper.getWritableDatabase();
    }

    public void insert(TableProMdl mdl) {
        try {
            db.execSQL(" INSERT INTO " + TABLE_TAB_PRO + " (ID,TABEL_TITLE,PRO_TOTAL,PRO_REVENUE,IS_PAY,CREATE_TIME,END_TIME,TAB_DATE,ORDER_ID) " + "VALUES (?,?,?,?,?,?,?,?,?)", new String[]{mdl.getID(), mdl.getTableTitle(),mdl.getProTotal(),mdl.getProRevenue(),mdl.getIsPay(),mdl.getStartTime(),mdl.getEndTime(),mdl.getTabDate(),mdl.getOrderId()});
             if (mdl.getProList() != null && mdl.getProList().size() > 0) {
                for (ProductMdl productMdl : mdl.getProList()) {
                    productMdl.setTableId(mdl.getID());
                    new ProductDao(this.mContext).insert(productMdl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del(TableProMdl mdl) {
        String id = mdl.getID();
        if (id != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_TAB_PRO + " WHERE ID=? ", new String[]{id});
                new ProductDao(this.mContext).pkDel(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(TableProMdl mdl){
        try {
            db.execSQL("UPDATE " + TABLE_TAB_PRO + " SET TABEL_TITLE=?,PRO_TOTAL=?,PRO_REVENUE=?,IS_PAY=?,CREATE_TIME=?,END_TIME=?,TAB_DATE=?,ORDER_ID=?" +"WHERE ID=?", new String[]{mdl.getTableTitle(),mdl.getProTotal(),mdl.getProRevenue(),mdl.getIsPay(),mdl.getStartTime(),mdl.getEndTime(),mdl.getTabDate(),mdl.getOrderId(),mdl.getID()});
            if (mdl.getProList() != null && mdl.getProList().size() > 0) {
                for (ProductMdl productMdl : mdl.getProList()) {
                    new ProductDao(this.mContext).update(productMdl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        String sql = "SELECT * FROM " + TABLE_TAB_PRO;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor.getCount();
    }

    public TableProMdl getMdlByID(String id) {
        TableProMdl mdl = null;
        if (id != null) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TAB_PRO + " WHERE ID=?", new String[]{id});
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        mdl = new TableProMdl();
                        mdl.setID(cursor.getString(cursor.getColumnIndex("ID")));
                        mdl.setTableTitle(cursor.getString(cursor.getColumnIndex("TABEL_TITLE")));
                        mdl.setProTotal(cursor.getString(cursor.getColumnIndex("PRO_TOTAL")));
                        mdl.setProRevenue(cursor.getString(cursor.getColumnIndex("PRO_REVENUE")));
                        mdl.setIsPay(cursor.getString(cursor.getColumnIndex("IS_PAY")));
                        mdl.setStartTime(cursor.getString(cursor.getColumnIndex("CREATE_TIME")));
                        mdl.setEndTime(cursor.getString(cursor.getColumnIndex("END_TIME")));
                        mdl.setTabDate(cursor.getString(cursor.getColumnIndex("TAB_DATE")));
                        mdl.setOrderId(cursor.getString(cursor.getColumnIndex("ORDER_ID")));

                        mdl.setProList(new ProductDao(this.mContext).pkListAll(id));
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mdl;
    }


    public List<TableProMdl> listAll() {
        List<TableProMdl> list = new ArrayList<TableProMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_TAB_PRO;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        TableProMdl mdl = new TableProMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setTableTitle(cursor.getString(1));
                        mdl.setProTotal(cursor.getString(2));
                        mdl.setProRevenue(cursor.getString(3));
                        mdl.setIsPay(cursor.getString(4));
                        mdl.setStartTime(cursor.getString(5));
                        mdl.setEndTime(cursor.getString(6));
                        mdl.setTabDate(cursor.getString(7));
                        mdl.setOrderId(cursor.getString(8));
                        mdl.setProList(new ProductDao(this.mContext).pkListAll(mdl.getID()));
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


    public ArrayList<TableProMdl> queryDateListAll(String date) {
        ArrayList<TableProMdl> list = new ArrayList<TableProMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_TAB_PRO + " WHERE TAB_DATE=?";
            Cursor cursor = db.rawQuery(sql, new String[]{date});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        TableProMdl mdl = new TableProMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setTableTitle(cursor.getString(1));
                        mdl.setProTotal(cursor.getString(2));
                        mdl.setProRevenue(cursor.getString(3));
                        mdl.setIsPay(cursor.getString(4));
                        mdl.setStartTime(cursor.getString(5));
                        mdl.setEndTime(cursor.getString(6));
                        mdl.setTabDate(cursor.getString(7));
                        mdl.setOrderId(cursor.getString(8));
                        mdl.setProList(new ProductDao(this.mContext).pkListAll(mdl.getID()));
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
