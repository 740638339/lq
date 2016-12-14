package com.lq.yl.product.count.app;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.util.SvcCallHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wb-liuquan.e on 2016/11/3.
 */
public class Test extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goIndentity();
    }

    public void goIndentity(){
        Response.Listener<String> scsLstn = new Response.Listener<String>() {
            @Override
            public void onResponse(String rsp) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener flrLstn = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*String errMsg = "Get error When Loading My Report";
                if (error != null) {
                    if (error.toString().startsWith("com.android.volley.ServerError"))
                        errMsg = errMsg + ": " + Test.this.getResources().getString(R.string.error_volley_server_error);
                    else if (error.toString().startsWith("com.android.volley.NoConnectionError"))
                        errMsg = errMsg + ": " + Test.this.getResources().getString(R.string.error_volley_noconn_error);
                    else if (error.toString().startsWith("com.android.volley.TimeoutError"))
                        errMsg = errMsg + ": " + Test.this.getResources().getString(R.string.error_volley_timeout_error);
                    else
                        errMsg = errMsg + ": " + error.toString();
                    //Toast.makeText(PrivilegeAct.this, errMsg, Toast.LENGTH_LONG).show();
                }*/
            }
        };
        try {
            Map<String, String> params = new HashMap<String, String>();
            SvcCallHelper.GetInstance(this).getStringRequest("http://30.1.40.14:8080/test/Action", scsLstn, flrLstn, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
