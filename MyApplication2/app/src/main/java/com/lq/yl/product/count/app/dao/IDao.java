package com.lq.yl.product.count.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lq.yl.product.count.app.helper.DBHelper;

import java.io.Serializable;

/**
 * Created by wb-liuquan.e on 2016/10/17.
 */
public class IDao implements Serializable{

    public DBHelper helper;
    public SQLiteDatabase db;
    public Context mContext;
    public IDao(){
    }
}
