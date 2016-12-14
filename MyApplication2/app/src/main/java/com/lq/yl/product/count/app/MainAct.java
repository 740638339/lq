package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.manager.ProMngAct;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.dlg.CommDlg;
import com.lq.yl.product.count.app.mdl.StoreProMdl;
import com.lq.yl.product.count.app.other.gest.UnlockGestPsdAct;

import java.util.List;

public class MainAct extends BaseAct implements View.OnClickListener {


    private TextView mStorePsnNme, mStoreTitle;
    private ImageView mAddTable;
    private LinearLayout mProMngInclud, mDiaryOrderInclud,mOrderChartInclud;

    private static MainAct mInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        mInstance = this;
    }

    public static MainAct getInstance(){
        return mInstance;
    }

    public void initView() {
        this.mStoreTitle = (TextView) this.findViewById(R.id.storeTitle);
        this.mStorePsnNme = (TextView) this.findViewById(R.id.storePsnNme);
        this.mAddTable = (ImageView) this.findViewById(R.id.add_table);

        this.mProMngInclud = (LinearLayout) this.findViewById(R.id.pro_mng_includ);
        this.mProMngInclud.setOnClickListener(this);
        TextView proMngName = (TextView) this.mProMngInclud.findViewById(R.id.inc_text_1);
        proMngName.setText("商品管理");

        this.mDiaryOrderInclud = (LinearLayout) this.findViewById(R.id.diary_order_includ);
        this.mDiaryOrderInclud.setOnClickListener(this);
        TextView diaryOrderName = (TextView) this.mDiaryOrderInclud.findViewById(R.id.inc_text_1);
        diaryOrderName.setText("今日订单");
        ImageView orderImgV = (ImageView) this.mDiaryOrderInclud.findViewById(R.id.inc_imgV_left);
        orderImgV.setBackgroundResource(R.mipmap.chart_icon);

        this.mOrderChartInclud = (LinearLayout) this.findViewById(R.id.order_chart_includ);
        this.mOrderChartInclud.setOnClickListener(this);
        TextView orderChartName = (TextView) this.mOrderChartInclud.findViewById(R.id.inc_text_1);
        orderChartName.setText("收入统计");
        ImageView chartImgV = (ImageView) this.mOrderChartInclud.findViewById(R.id.inc_imgV_left);
        chartImgV.setBackgroundResource(R.mipmap.dilary_icon);

        mAddTable.setOnClickListener(this);

    }

    public void initData() {

        List<StoreProMdl> list = new StoreProDao(this).listAll();
        StoreProMdl storeMdl = list.get(0);
        mStoreTitle.setText(storeMdl.getStoreName());
        mStorePsnNme.setText(storeMdl.getStoreDec());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.add_table:
                if (new ProductDao(this).productList().size() < 1) {
                    showToast("先添加商品");
                } else {
                    intent.setClass(this, ListTableAct.class);
                    startActivity(intent);
                }
                break;
            case R.id.pro_mng_includ:
                intent.setClass(this, UnlockGestPsdAct.class);
                intent.putExtra("code", ProMngAct.LOCK_PSD_PRO_CODE);
                startActivity(intent);
                break;
            case R.id.diary_order_includ:
                intent.setClass(this, ListDilaryOrderAct.class);
                startActivity(intent);
                break;
            case R.id.order_chart_includ:
                intent.setClass(this, UnlockGestPsdAct.class);
                intent.putExtra("code",ChartAct.LOCK_PSD_CHART_CODE);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CrtProAct.getInstance() != null) {
            CrtProAct.getInstance().finish();
        }
        if (LogonAct.getInstance() != null) {
            LogonAct.getInstance().finish();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommDlg dlg = new CommDlg(this, "确认退出？", "");
            dlg.setCanceledOnTouchOutside(true);
            Window win0 = dlg.getWindow();
            win0.setGravity(Gravity.CENTER);
            win0.setWindowAnimations(R.style.Comm_Dlg);
            dlg.show();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
