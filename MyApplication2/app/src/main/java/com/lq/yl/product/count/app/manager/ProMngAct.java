package com.lq.yl.product.count.app.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.BaseAct;
import com.lq.yl.product.count.app.CrtProAct;
import com.lq.yl.product.count.app.adapter.LvProductInfoAdpt;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.other.gest.UnlockGestPsdAct;

import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/11/1.
 */
public class ProMngAct extends BaseAct implements View.OnClickListener {

    public static String LOCK_PSD_PRO_CODE = "LOCK_PSD_PRO_CODE";
    private ListView mProductSimpLv;
    private List<ProductMdl> mLists;
    private LvProductInfoAdpt mAdapter;
    private TextView mAddOrUpdateTxtV;
    private ImageView mBackImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_mng_act_layout);

        this.mProductSimpLv = (ListView) this.findViewById(R.id.prodct_simp_lv);
        this.mAddOrUpdateTxtV = (TextView) this.findViewById(R.id.addOrUpdateTxtV);
        this.mBackImg = (ImageView) this.findViewById(R.id.backImg);
        this.mBackImg.setOnClickListener(this);
        this.mAddOrUpdateTxtV.setOnClickListener(this);
    }

    public void initData() {

        mLists = new ProductDao(this).productList();

        mAdapter = new LvProductInfoAdpt(this, mLists);

        mProductSimpLv.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImg:
                this.finish();
                break;
            case R.id.addOrUpdateTxtV:
                Intent intent = new Intent();
                intent.setClass(ProMngAct.this, CrtProAct.class);
                if (new ProductDao(ProMngAct.this).getCount() > 0) {
                    intent.putExtra("isEdt", true);
                }
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (new ProductDao(this).getCount() < 1) {
            this.mAddOrUpdateTxtV.setText("创建");
        }

        if(UnlockGestPsdAct.getInstance() != null){
            UnlockGestPsdAct.getInstance().finish();
        }

        initData();
    }
}
