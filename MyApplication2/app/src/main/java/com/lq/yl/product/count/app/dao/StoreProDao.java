package com.lq.yl.product.count.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lq.yl.product.count.app.helper.DBHelper;
import com.lq.yl.product.count.app.mdl.StoreProMdl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wb-liuquan.e on 2016/10/24.
 */
public class StoreProDao extends IDao{
    public final static String TABLE_STORE_INFO = "TABLE_STORE_INFO";
    public final static String CREATE_TABLE_STORE_INFO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STORE_INFO
            + " (ID VARCHAR PRIMARY KEY, "
            + "STORENAME VARCHAR, "
            + "STOREDEC VARCHAR, "
            + "CREATE_TIME VARCHAR); ";

    public StoreProDao(Context context) {
        this.helper = new DBHelper(context);
        db = this.helper.getWritableDatabase();
    }

    public void insert(StoreProMdl mdl){
        try {
            mdl.setID(UUID.randomUUID().toString());
            db.execSQL("INSERT INTO " + TABLE_STORE_INFO + " (ID,STORENAME,STOREDEC,CREATE_TIME) " +"VALUES (?,?,?,?)", new String[]{mdl.getID(),mdl.getStoreName(),mdl.getStoreDec(),mdl.getCreateTime()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del(StoreProMdl mdl) {
        String id = mdl.getID();
        if (id != null) {
            try {
                db.execSQL("DELETE FROM " + TABLE_STORE_INFO + " WHERE ID = ?", new String[]{id});
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void update(StoreProMdl mdl){
        try {
            db.execSQL("UPDATE " + TABLE_STORE_INFO + " SET STORENAME=?,STOREDEC=?,CREATE_TIME=?" +
                    "WHERE ID=?", new String[]{mdl.getStoreName(),mdl.getStoreDec(),mdl.getCreateTime(),mdl.getID()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount(){
        String sql = "SELECT * FROM " + TABLE_STORE_INFO;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor.getCount();
    }

    public StoreProMdl getMdlByID(String id){
        StoreProMdl mdl = null;
        if (id != null) {
            try {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STORE_INFO + " WHERE ID = ?", new String[]{id});
                if(cursor != null) {
                    if (cursor.moveToNext()) {
                        mdl = new StoreProMdl();
                        mdl.setID(cursor.getString(cursor.getColumnIndex("ID")));
                        mdl.setStoreName(cursor.getString(cursor.getColumnIndex("STORENAME")));
                        mdl.setStoreDec(cursor.getString(cursor.getColumnIndex("STOREDEC")));
                        mdl.setStoreDec(cursor.getString(cursor.getColumnIndex("STOREDEC")));
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return mdl;
    }


    public List<StoreProMdl> listAll(){
        List<StoreProMdl> list = new ArrayList<StoreProMdl>();
        try {
            String sql = "SELECT * FROM " + TABLE_STORE_INFO;
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    int i = cursor.getCount();
                    for (int j = 0; j < i; j++) {
                        cursor.moveToPosition(j);
                        StoreProMdl mdl = new StoreProMdl();
                        mdl.setID(cursor.getString(0));
                        mdl.setStoreName(cursor.getString(1));
                        mdl.setStoreDec(cursor.getString(2));
                        list.add(mdl);
                    }
                }
                cursor.close();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
}
