package com.lq.yl.product.count.app.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lq.yl.product.count.app.ProductApp;

/**
 * Created by wb-liuquan.e on 2016/11/16.
 */
public class SysPreferenceManager {

    private static SysPreferenceManager Instance;

    private static SharedPreferences Preference;
    public static Editor editor;
    private static Object Lock = new Object();

    public static final String ONLINE_ENVIRONMENT = "ONLINE_ENVIRONMENT";
    public static final String HAS_NEW_ZNJT_MESSAGE = "HAS_NEW_ZNJT_MESSAGE";
    public static final String HAS_NEW_DZZJ_MESSAGE = "HAS_NEW_DZZJ_MESSAGE";
    public static final String BOUND_ACCOUNT = "BOUND_ACCOUNT";

    public static final String TOOL_TIP_VISION = "0";

    public static final String EXT_FUN_WEBVIEW_TOOL_TIP = "EXT_FUN_WEBVIEW_TOOL_TIP";




    public static SysPreferenceManager getInstance() {
        if (Instance == null) {
            synchronized (Lock) {
                if (Instance == null) {
                    Instance = new SysPreferenceManager();
                }
                Preference = android.preference.PreferenceManager.getDefaultSharedPreferences(ProductApp.getInstance().getApplicationContext());
                editor = Preference.edit();
            }
        }
        return Instance;
    }

    public boolean commitPreference() {
        return editor.commit();
    }

    public void setIsNewFun(boolean isNewFun, String velue) {
        editor.putBoolean(velue, isNewFun);
        this.commitPreference();
    }

    public boolean hasNewZNJTMsg() {
        return Preference.getBoolean(HAS_NEW_ZNJT_MESSAGE, false);
    }


}
