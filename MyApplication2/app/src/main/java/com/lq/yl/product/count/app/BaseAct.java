package com.lq.yl.product.count.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.lq.yl.product.count.app.util.ActStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/13.
 */
public class BaseAct extends Activity{

    public static List<TableProMdl> mTableProList = new ArrayList<TableProMdl>();


    public void initView(){}
    public void initData(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActStack.getInstance().push(this);
    }
    protected void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
}
