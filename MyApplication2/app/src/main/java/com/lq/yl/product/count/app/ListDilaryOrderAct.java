package com.lq.yl.product.count.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.LvDilaryAdpt;
import com.lq.yl.product.count.app.dao.OrderDao;
import com.lq.yl.product.count.app.dao.TabProDao;
import com.lq.yl.product.count.app.mdl.OrderMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.lq.yl.product.count.app.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ListDilaryOrderAct extends Activity {

    private ListView mDilaryOrderLv;
    private LvDilaryAdpt mAdapter;
    private ArrayList<TableProMdl> mTabList;
    private ImageView mAddTable, mBackImg;

    private OrderMdl mMdl;
    private TextView mNullTxtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_dilary_order_act_layout);

        initView();
        initData();
    }


    public void initView() {
        mAddTable = (ImageView) this.findViewById(R.id.add_table);
        mBackImg = (ImageView) this.findViewById(R.id.backImg);
        mDilaryOrderLv = (ListView) this.findViewById(R.id.dilary_order_Lv);
        mNullTxtv = (TextView) this.findViewById(R.id.nullTxtv);
    }

    public void initData() {
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDilaryOrderAct.this.finish();
            }
        });

        List<OrderMdl> lists = new OrderDao(this).orderList();
        if(lists.size() > 0){
            mNullTxtv.setVisibility(View.GONE);
            mDilaryOrderLv.setVisibility(View.VISIBLE);
            for (OrderMdl mdl : lists) {
                if(mdl.getCreateDate().equals(DateUtils.getCrtDate())){
                    mMdl = mdl;
                    break;
                }
            }
            if(mMdl != null){
                mTabList = new TabProDao(this).queryDateListAll(mMdl.getCreateDate());
                mAdapter = new LvDilaryAdpt(this, mTabList);

                mDilaryOrderLv.setAdapter(mAdapter);
                mDilaryOrderLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TableProMdl mdl = mTabList.get(position);
                        Intent intent = new Intent();
                        intent.setClass(ListDilaryOrderAct.this,OrderDetailAct.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tableProMdl", mdl);//传递当前的桌位
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
