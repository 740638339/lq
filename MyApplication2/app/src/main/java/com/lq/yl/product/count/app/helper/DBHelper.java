package com.lq.yl.product.count.app.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.lq.yl.product.count.app.dao.OrderDao;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.dao.TabProDao;

import java.io.File;
import java.io.IOException;

/**
 * Created by wb-liuquan.e on 2016/10/24.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lq_product.db";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper Instance;
    private Context mContext;

    public SQLiteDatabase mDatabase;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static DBHelper GetInstance(Context context) {
        if (Instance != null) {
            return Instance;
        } else {
            Instance = new DBHelper(context);
            return Instance;
        }
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductDao.CREATE_TABLE_PRODUCT_INFO);
        db.execSQL(StoreProDao.CREATE_TABLE_STORE_INFO);
        db.execSQL(TabProDao.CREATE_TABLE_TAB_PRO);
        db.execSQL(OrderDao.CREATE_TABLE_ORDER_INFO);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        synchronized (this) {
            return getDatabaseLocked(false);
        }
    }

    private SQLiteDatabase getDatabaseLocked(boolean writable) {

        SQLiteDatabase db = null;

        String fileDataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + this.mContext.getPackageName()
                + "/file/";
        File file = new File(fileDataPath, DB_NAME);
        File dir = new File(file.getParent());

        boolean isFileCreateSuccess = false;
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            try {
                isFileCreateSuccess = file.createNewFile();
                if (isFileCreateSuccess) {
                    db = SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
                    db.execSQL(ProductDao.CREATE_TABLE_PRODUCT_INFO);
                    db.execSQL(StoreProDao.CREATE_TABLE_STORE_INFO);
                    db.execSQL(TabProDao.CREATE_TABLE_TAB_PRO);
                    db.execSQL(OrderDao.CREATE_TABLE_ORDER_INFO);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return super.getReadableDatabase();
            }

        }else{
            db = SQLiteDatabase.openOrCreateDatabase(file.getPath(), null);
            db.execSQL(ProductDao.CREATE_TABLE_PRODUCT_INFO);
            db.execSQL(StoreProDao.CREATE_TABLE_STORE_INFO);
            db.execSQL(TabProDao.CREATE_TABLE_TAB_PRO);
            db.execSQL(OrderDao.CREATE_TABLE_ORDER_INFO);
        }

        final int version = db.getVersion();
        if (version != DATABASE_VERSION) {
            db.beginTransaction();
            try {
                if (version == 0) {
                    onCreate(db);
                } else {
                    if (DATABASE_VERSION > version) {
                        onUpgrade(db, version, DATABASE_VERSION);
                    }
                }
                db.setVersion(DATABASE_VERSION);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        return db;
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //checkColumnExist1(db,"TABLE_PRODUCT_INFO","");
        //db.execSQL("ALTER TABLE TABLE_PRODUCT_INFO ADD BBB VARCHAR(10)");
    }

    private static boolean checkColumnExist1(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0", null );
            result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }
}