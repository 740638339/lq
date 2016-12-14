package com.lq.yl.product.count.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.LvProSaleDetalAdpt;
import com.lq.yl.product.count.app.adapter.LvProductSaleAdpt;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAct extends BaseAct {

    private List<ProductMdl> mList = new ArrayList<>();
    private ListView mProdctSaleLv;
    private LvProSaleDetalAdpt mAdapter;

    private TextView mTotalTxtV, mCloseTxtV, mWelcomeTxtV;
    private ImageView mBackImg;
    private TableProMdl mTableProMdl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_act_layout);

        initView();

        initData();
    }

    public void initView() {
        mTotalTxtV = (TextView) this.findViewById(R.id.totalTxtV);
        mCloseTxtV = (TextView) this.findViewById(R.id.closeTxtV);
        mBackImg = (ImageView) this.findViewById(R.id.backImg);
        mWelcomeTxtV = (TextView) this.findViewById(R.id.welcomeTxtV);
        mProdctSaleLv = (ListView) this.findViewById(R.id.prodct_sale_lv);

        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailAct.this.finish();
            }
        });
        mCloseTxtV.setVisibility(View.INVISIBLE);
    }

    public void initData() {
        mTableProMdl = (TableProMdl) getIntent().getSerializableExtra("tableProMdl");

        for (ProductMdl productMdl : mTableProMdl.getProList()) {
            if (productMdl.getProNum() != null && !productMdl.getProNum().equals("0")) {
                mList.add(productMdl);
            }
        }

        mAdapter = new LvProSaleDetalAdpt(this, mList);

        mProdctSaleLv.setAdapter(mAdapter);

        mTotalTxtV.setText("总计消费：" + LvProductSaleAdpt.getInstance().getTotal()[0] + "元");
    }
}
