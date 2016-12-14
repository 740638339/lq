package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.LvProductSaleAdpt;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.TabProDao;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.lq.yl.product.count.app.util.DateUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddTableProAct extends BaseAct implements View.OnClickListener {

    private List<ProductMdl> mList;
    private ListView mProdctSaleLv;
    private LvProductSaleAdpt mAdapter;

    private TextView mTablePay, mActTitle;
    private ImageView mBackImg;
    private Button mSaveBtn;

    private TableProMdl mTableProMdl;

    private static AddTableProAct instance;

    private String mID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_table_act_layout);

        mProdctSaleLv = (ListView) this.findViewById(R.id.prodctSaleLv);
        mTablePay = (TextView) this.findViewById(R.id.tablePay);
        mActTitle = (TextView) this.findViewById(R.id.act_title);
        mBackImg = (ImageView) this.findViewById(R.id.backImg);
        mSaveBtn = (Button) this.findViewById(R.id.saveBtn);

        mBackImg.setOnClickListener(this);
        mTablePay.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);

        if (getIntent() != null) {
            mTableProMdl = (TableProMdl) getIntent().getSerializableExtra("tableProMdl");
        }
        if (mTableProMdl != null) {
            mList = new ProductDao(this).pkListAll(mTableProMdl.getID());
            mActTitle.setText("编辑商品");
        } else {
            mList = new ProductDao(this).productList();
            mActTitle.setText("添加商品");
        }
        mAdapter = new LvProductSaleAdpt(this, mList);

        mProdctSaleLv.setAdapter(mAdapter);

        instance = this;

        mID = UUID.randomUUID().toString();
    }


    public static AddTableProAct getInstance() {
        return instance;
    }

    private boolean mFlag = false;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.backImg:
                this.finish();
                break;
            case R.id.tablePay:

                countProNum();

                for (int i = 0; i < mList.size(); i++) {
                    ProductMdl mdl = mList.get(i);
                    if (mdl.getProNum() != null && !mdl.getProNum().equals("0")) {
                        mFlag = true;
                        break;
                    } else if (i == mList.size() - 1) {
                        Toast.makeText(this, "请先添加商品", Toast.LENGTH_SHORT).show();
                    }
                }

                if (mFlag) {
                    Intent tableIntent = new Intent(this, BillDetailAct.class);

                    Bundle bundle = new Bundle();

                    if (this.mTableProMdl != null) {//

                        mTableProMdl.setProList(mList);
                        mTableProMdl.setProTotal(LvProductSaleAdpt.getInstance().getTotal()[0] + "");
                        mTableProMdl.setProRevenue(LvProductSaleAdpt.getInstance().getTotal()[1] + "");
                        new TabProDao(this).update(mTableProMdl);
                        bundle.putSerializable("tableProMdl", this.mTableProMdl);//传递当前的桌位
                    } else {//为创建页面

                        bundle.putSerializable("tableProMdl", crtInsertTab());
                    }

                    tableIntent.putExtras(bundle);
                    startActivity(tableIntent);
                }
                break;
            case R.id.saveBtn:
                mSaveBtn.setEnabled(false);
                Intent intent = new Intent();
                if (mTableProMdl != null) {
                    mTableProMdl.setProList(mList);
                    mTableProMdl.setProTotal(LvProductSaleAdpt.getInstance().getTotal()[0] + "");
                    mTableProMdl.setProRevenue(LvProductSaleAdpt.getInstance().getTotal()[1] + "");

                    new TabProDao(this).update(mTableProMdl);
                } else {

                    crtInsertTab();
                }

                countProNum();


                intent.setClass(this, ListTableAct.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void countProNum() {
        List<ProductMdl> lists = new ProductDao(AddTableProAct.this).productList();
        Map<String, String> map = LvProductSaleAdpt.getInstance().getAllProNumMap();

        for (int i = 0; i < lists.size(); i++) {//将初始的所有商品遍历
            ProductMdl mdl = lists.get(i);
            for (String key: map.keySet()){ //将所有商品（key）  对应的数量 （value） 遍历
                if(key.equals(mdl.getProName())){//将商品的数量汇总
                    mdl.setProSaleSum((Double.parseDouble(mdl.getProSaleSum()) + Double.parseDouble(map.get(key)))+"");
                }
            }
        }
    }

    private boolean checkSameTab(TableProMdl mdl){
        boolean flag = false;
        List<TableProMdl> lists = new TabProDao(this).listAll();
        for (TableProMdl tableProMdl :lists){
            if(tableProMdl.getID().equals(mdl.getID())){
                flag = true;
            }
        }
        return flag;
    }

    public TableProMdl crtInsertTab(){
        TableProMdl mdl = new TableProMdl();
        mdl.setID(mID);
        mdl.setProList(mList);
        boolean b = checkSameTab(mdl);

        if(b){
            mdl.setTableTitle(new TabProDao(this).getCount() + "");
        }else{
            mdl.setTableTitle((new TabProDao(this).getCount() + 1) + "");
        }

        double[] total = LvProductSaleAdpt.getInstance().getTotal();

        mdl.setProTotal((total[0]) + "");
        mdl.setProRevenue((total[1]) + "");
        mdl.setStartTime(DateUtils.getCrtTime());
        mdl.setTabDate(DateUtils.getCrtDate());


        if(b){
            new TabProDao(AddTableProAct.this).update(mdl);
        }else{
            new TabProDao(this).insert(mdl);
        }
        return mdl;
    }
}
