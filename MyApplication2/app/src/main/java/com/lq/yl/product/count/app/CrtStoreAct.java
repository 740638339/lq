package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.mdl.StoreProMdl;
import com.lq.yl.product.count.app.util.DateUtils;

import java.util.List;

public class CrtStoreAct extends BaseAct {

    private static CrtStoreAct instance;
    private EditText mGXQMEdt, mStoreNameEdt;
    private LinearLayout mIncLayout;
    private TextView mSaveTxtV, mStorePsnNameTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crt_store_act_layout);

        initView();

        initData();
    }

    public void initView() {
        mGXQMEdt = (EditText) findViewById(R.id.gxqmEdt);

        mIncLayout = (LinearLayout) findViewById(R.id.storeNameClude);
        mStorePsnNameTxtV = (TextView) this.mIncLayout.findViewById(R.id.lblTxtV);
        mStoreNameEdt = (EditText) this.mIncLayout.findViewById(R.id.contentEdt);
        mSaveTxtV = (TextView) this.findViewById(R.id.saveTxtV);
    }

    public void initData() {

        mStorePsnNameTxtV.setText("店铺名称：");
        mSaveTxtV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CrtStoreAct.this.mStoreNameEdt.getText() == null || CrtStoreAct.this.mStoreNameEdt.getText().toString().trim().equals("")) {
                    showToast("请输入" + getResources().getString(R.string.store_name));
                } else if (CrtStoreAct.this.mGXQMEdt.getText() == null) {
                    showToast("请输入" + getResources().getString(R.string.store_person_name));
                } else {

                    List<StoreProMdl> lists = new StoreProDao(CrtStoreAct.this).listAll();
                    if(lists.size() > 0){
                        StoreProMdl mdl = lists.get(0);
                        mdl.setStoreDec(mGXQMEdt.getText() == null ? "" : mGXQMEdt.getText().toString());
                        mdl.setStoreName(mStoreNameEdt.getText() == null ? "" : mStoreNameEdt.getText().toString());
                        mdl.setCreateTime(DateUtils.getCrtDateAndTime());
                        new StoreProDao(CrtStoreAct.this).update(mdl);
                    }else{
                        StoreProMdl mdl = new StoreProMdl();
                        mdl.setStoreDec(mGXQMEdt.getText() == null ? "" : mGXQMEdt.getText().toString());
                        mdl.setStoreName(mStoreNameEdt.getText() == null ? "" : mStoreNameEdt.getText().toString());
                        mdl.setCreateTime(DateUtils.getCrtDateAndTime());
                        new StoreProDao(CrtStoreAct.this).insert(mdl);
                    }
                    if (new StoreProDao(CrtStoreAct.this).listAll().size() < 1) {
                        showToast("创建店铺失败");
                    }else{
                        startActivity(new Intent(CrtStoreAct.this,CrtProAct.class));
                    }
                }
            }
        });

        instance = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StartAct.getInstance() != null) {
            StartAct.getInstance().finish();
        }

    }

    public static CrtStoreAct getInstance() {
        return instance;
    }
}
