package com.lq.yl.product.count.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lq.yl.product.count.app.helper.DBHelper;
import com.lq.yl.product.count.app.mdl.OrderMdl;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wb-liuquan.e on 2016/11/9.
 */
public class OrderDao extends IDao{
    public final static String TABLE_ORDER_INFO = "TABLE_ORDER_INFO";
    public final static String CREATE_TABLE_ORDER_INFO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ORDER_INFO
            + " ("
            + "ID                   VARCHAR PRIMARY KEY, "
            + "COUNT                VARCHAR,"
            + "ACT_REVENUE_TOTAL    VARCHAR,"
            + "REVENUE_TOTAL        VARCHAR,"
            + "YEAR                 VARCHAR,"
            + "MONTH                VARCHAR,"
            + "DAY                  VARCHAR,"
            + "WEEKDAY              VARCHAR,"
            + "CREATE_DATE          VARCHAR); ";

    public OrderDao(Context context) {
        // helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        this.mContext = context;
        this.helper = new DBHelper(context);
        db = this.helper.getWritableDatabase();
    }

    public void insert(OrderMdl mdl) {
        try {
            mdl.setID(UUID.randomUUID().toString());
            db.execSQL("INSERT INTO " + TABLE_ORDER_INFO
                            + " (ID,COUNT,ACT_REVENUE_TOTAL,REVENUE_TOTAL,YEAR,MONTH,DAY,WEEKDAY,CREATE_DATE) " +"VALUES (?,?,?,?,?,?,?,?,?)",
                    new String[]{mdl.getID(), mdl.getCount(), mdl.getActRevenueTotal(), mdl.getRevenueTotal(),mdl.getYear(),mdl.getMonth(),mdl.getDay(),mdl.getWeekDay(),mdl.getCreateDate()});
            if (mdl.getTableList() != null && mdl.getTableList().size() > 0) {
                for (TableProMdl tableProMdl : mdl.getTableList()) {
                    tableProMdl.setOrderId(mdl.getID());
                    new TabProDao(this.mContext).update(tableProMdl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Id删除商品
    public void del(String id) {
        if (id != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_ORDER_INFO + " WHERE ID = ?", new String[]{id});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //根据外键Id删除商品
    public void pkDel(String pkId) {
        if (pkId != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_ORDER_INFO + " WHERE TABLE_ID=?", new String[]{pkId});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(OrderMdl mdl) {
        try {
            db.execSQL("UPDATE " + TABLE_ORDER_INFO + " SET COUNT=?,ACT_REVENUE_TOTAL=?,REVENUE_TOTAL=?,YEAR=?,MONTH=?,DAY=?,WEEKDAY=?,CREATE_DATE=?" + " WHERE ID=? ",
                    new String[]{mdl.getCount(), mdl.getActRevenueTotal(), mdl.getRevenueTotal(),mdl.getYear(),mdl.getMonth(),mdl.getDay(),mdl.getWeekDay(), mdl.getCreateDate(), mdl.getID()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderMdl queryMdlByID(String id) {
        OrderMdl mdl = null;
        if (id != null) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER_INFO + " WHERE ID=?", new String[]{id});
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        mdl = new OrderMdl();
                        mdl.setID(cursor.getString(cursor.getColumnIndex("ID")));
                        mdl.setCount(cursor.getString(cursor.getColumnIndex("COUNT")));
                        mdl.setActRevenueTotal(cursor.getString(cursor.getColumnIndex("ACT_REVENUE_TOTAL")));
                        mdl.setRevenueTotal(cursor.getString(cursor.getColumnIndex("REVENUE_TOTAL")));
                        mdl.setYear(cursor.getString(cursor.getColumnIndex("YEAR")));
                        mdl.setMonth(cursor.getString(cursor.getColumnIndex("MONTH")));
                        mdl.setDay(cursor.getString(cursor.getColumnIndex("DAY")));
                        mdl.setWeekDay(cursor.getString(cursor.getColumnIndex("WEEKDAY")));
                        mdl.setCreateDate(cursor.getString(cursor.getColumnIndex("CREATE_DATE")));
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mdl;
    }

    public OrderMdl queryMdlByDate(String date) {
        OrderMdl mdl = null;
        if (date != null) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER_INFO + " WHERE CREATE_DATE=?", new String[]{date});
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        mdl = new OrderMdl();
                        mdl.setID(cursor.getString(cursor.getColumnIndex("ID")));
                        mdl.setCount(cursor.getString(cursor.getColumnIndex("COUNT")));
                        mdl.setActRevenueTotal(cursor.getString(cursor.getColumnIndex("ACT_REVENUE_TOTAL")));
                        mdl.setRevenueTotal(cursor.getString(cursor.getColumnIndex("REVENUE_TOTAL")));
                        mdl.setYear(cursor.getString(cursor.getColumnIndex("YEAR")));
                        mdl.setMonth(cursor.getString(cursor.getColumnIndex("MONTH")));
                        mdl.setDay(cursor.getString(cursor.getColumnIndex("DAY")));
                        mdl.setWeekDay(cursor.getString(cursor.getColumnIndex("WEEKDAY")));
                        mdl.setCreateDate(cursor.getString(cursor.getColumnIndex("CREATE_DATE")));

                        ArrayList<TableProMdl> list = new TabProDao(this.mContext).queryDateListAll(mdl.getCreateDate());
                        mdl.setTableList(list);
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
        String sql = "SELECT * FROM " + TABLE_ORDER_INFO;
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //根据id商品信息数量
    public int QueryByIdCount(String id) {
        int count = 0;
        if (id != null) {
            String sql = "SELECT * FROM " + TABLE_ORDER_INFO + " WHERE ID=?";
            Cursor cursor = db.rawQuery(sql, new String[]{id});

            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public List<OrderMdl> orderList() {
        List<OrderMdl> list = new ArrayList<OrderMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_ORDER_INFO;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        OrderMdl mdl = new OrderMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setCount(cursor.getString(1));
                        mdl.setActRevenueTotal(cursor.getString(2));
                        mdl.setRevenueTotal(cursor.getString(3));
                        mdl.setYear(cursor.getString(4));
                        mdl.setMonth(cursor.getString(5));
                        mdl.setDay(cursor.getString(6));
                        mdl.setWeekDay(cursor.getString(7));
                        mdl.setCreateDate(cursor.getString(8));
                        list.add(mdl);
                    }
                }
            }
            cursor.close();
    }catch(Exception ex){
        ex.printStackTrace();
    }

    return list;
}

    public List<OrderMdl> pkListAll(String id) {
        List<OrderMdl> list = new ArrayList<OrderMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_ORDER_INFO + " WHERE TABLE_ID=?";
            Cursor cursor = db.rawQuery(sql, new String[]{id});
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        OrderMdl mdl = new OrderMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setCount(cursor.getString(1));
                        mdl.setActRevenueTotal(cursor.getString(2));
                        mdl.setRevenueTotal(cursor.getString(3));
                        mdl.setYear(cursor.getString(4));
                        mdl.setMonth(cursor.getString(5));
                        mdl.setDay(cursor.getString(6));
                        mdl.setWeekDay(cursor.getString(7));
                        mdl.setCreateDate(cursor.getString(8));
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


    public ArrayList<OrderMdl> getIntervalList(String crtDate,String otherDate){
        ArrayList<OrderMdl> list = new ArrayList<OrderMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_ORDER_INFO + " WHERE CREATE_DATE BETWEEN +'"+otherDate +"' AND '"+ crtDate+"'";
            Cursor cursor = db.rawQuery(sql,null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        OrderMdl mdl = new OrderMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setCount(cursor.getString(1));
                        mdl.setActRevenueTotal(cursor.getString(2));
                        mdl.setRevenueTotal(cursor.getString(3));
                        mdl.setYear(cursor.getString(4));
                        mdl.setMonth(cursor.getString(5));
                        mdl.setDay(cursor.getString(6));
                        mdl.setWeekDay(cursor.getString(7));
                        mdl.setCreateDate(cursor.getString(8));
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


    public String getIntervalTotal(String crtDate,String otherDate){
        String count = "0";
        String sql = "SELECT SUM(REVENUE_TOTAL) AS TOTAL FROM " + TABLE_ORDER_INFO + " WHERE CREATE_DATE BETWEEN '"+otherDate +"' AND '"+ crtDate+"'";
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToNext()){
            count = cursor.getString(cursor.getColumnIndex("TOTAL"));
        }
        cursor.close();
        return count;
    }

}
