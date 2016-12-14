package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.widget.Toast;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.StoreProMdl;
import com.lq.yl.product.count.app.mdl.User;
import com.lq.yl.product.count.app.util.DateUtils;

import java.util.ArrayList;

public class StartAct extends BaseAct {

    private static StartAct instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_act_layout);

        instance = this;

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                User user = User.ReadUser(StartAct.this);

                if(user != null){
                    int i = DateUtils.compare_date(user.getLastLogonTime(), user);
                    if(i == 1){
                        if(new StoreProDao(StartAct.this).getCount() == 0 ){
                            startActivity(new Intent(StartAct.this,CrtStoreAct.class));
                        }else if(new ProductDao(StartAct.this).getCount() == 0){
                            startActivity(new Intent(StartAct.this,CrtProAct.class));
                        }else{
                            startActivity(new Intent(StartAct.this,MainAct.class));
                        }
                    }else{
                        showToast("身份过期，请重新登录");
                        startActivity(new Intent(StartAct.this, LogonAct.class));
                    }
                }else{
                    startActivity(new Intent(StartAct.this,LogonAct.class));
                }

            }
        }.start();
    }

    public static StartAct getInstance(){
        return instance;
    }
}
