package com.lq.yl.product.count.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.yl.product.count.R;

public class MngStoreInfoAct extends BaseAct implements View.OnClickListener{

    private LinearLayout mProCountClude, mPurchTotalPriceClude,mSaleTotalPriceClude,mSaleStateClude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mng_store_info_act_layout);


        this.mProCountClude = (LinearLayout) this.findViewById(R.id.proCountClude);
        this.mProCountClude.setOnClickListener(this);
        TextView proCountLbl = (TextView) this.mProCountClude.findViewById(R.id.inc_text_1);
        proCountLbl.setText("产品数量");
        TextView proCountTxtV = (TextView) this.mProCountClude.findViewById(R.id.inc_text_2);
        proCountTxtV.setText("12");

        this.mPurchTotalPriceClude = (LinearLayout) this.findViewById(R.id.purchTotalPriceClude);
        this.mPurchTotalPriceClude.setOnClickListener(this);
        TextView purchTotalPriceLbl = (TextView) this.mPurchTotalPriceClude.findViewById(R.id.inc_text_1);
        purchTotalPriceLbl.setText("商品进价总额");
        TextView purchTotalPriceTxtV = (TextView) this.mPurchTotalPriceClude.findViewById(R.id.inc_text_2);
        purchTotalPriceTxtV.setText("1234");

        this.mSaleTotalPriceClude = (LinearLayout) this.findViewById(R.id.saleTotalPriceClude);
        this.mSaleTotalPriceClude.setOnClickListener(this);
        TextView saleTotalPriceLbl = (TextView) this.mSaleTotalPriceClude.findViewById(R.id.inc_text_1);
        saleTotalPriceLbl.setText("商品售价总额");
        TextView saleTotalPriceTxtV = (TextView) this.mSaleTotalPriceClude.findViewById(R.id.inc_text_2);
        saleTotalPriceTxtV.setText("1234");

        this.mSaleStateClude = (LinearLayout) this.findViewById(R.id.saleStateClude);
        this.mSaleStateClude.setOnClickListener(this);
        TextView saleStateCludeLbl = (TextView) this.mSaleStateClude.findViewById(R.id.inc_text_1);
        saleStateCludeLbl.setText("商品销售状况");
        TextView saleStateCludeTxtV = (TextView) this.mSaleStateClude.findViewById(R.id.inc_text_2);
        saleStateCludeTxtV.setText("查看");



    }

    @Override
    public void onClick(View v) {

    }
}
