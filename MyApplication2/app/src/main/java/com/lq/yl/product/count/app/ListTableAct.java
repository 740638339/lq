package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.LvTableAdapter;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.dao.TabProDao;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.lq.yl.product.count.app.other.gest.UnlockGestPsdAct;

import java.util.ArrayList;
import java.util.List;

public class ListTableAct extends BaseAct {

    private ExpandableListView mEdtTableLv;
    private LvTableAdapter mAdapter;
    private ArrayList<List<ProductMdl>> mChildLists;
    private ImageView mAddTable, mBackImg;

    private boolean isDiary = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_table_act_layout);

        initView();

    }

    public void initView() {
        mAddTable = (ImageView) this.findViewById(R.id.add_table);
        mBackImg = (ImageView) this.findViewById(R.id.backImg);
        mEdtTableLv = (ExpandableListView) this.findViewById(R.id.edtTableLv);
    }

    public void initData() {
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListTableAct.this.finish();
            }
        });
        mAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListTableAct.this, AddTableProAct.class));
            }
        });

        List<TableProMdl> lists = new TabProDao(this).listAll();
        final List<TableProMdl> mGroupLists = new ArrayList<TableProMdl>();
        for (TableProMdl mdl:lists) {
            if(mdl.getIsPay().equals("0")){
                mGroupLists.add(mdl);
            }
        }
        if (mGroupLists.size() > 0) {
            mChildLists = new ArrayList<List<ProductMdl>>();
            for (int i = 0; i < mGroupLists.size(); i++) {
                TableProMdl mdl = mGroupLists.get(i);
                //mdl.setTableTitle(i + "");
                mChildLists.add(new ProductDao(this).pkListAll(mdl.getID()));
            }
        }
        mAdapter = new LvTableAdapter(this, mChildLists, mGroupLists);

        mEdtTableLv.setAdapter(mAdapter);
        mEdtTableLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                TableProMdl mdl = mGroupLists.get(groupPosition);
                Intent intent = new Intent();
                intent.setClass(ListTableAct.this, AddTableProAct.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tableProMdl", mdl);//传递当前的桌位
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AddTableProAct.getInstance() != null) {
            AddTableProAct.getInstance().finish();
        }
        if(UnlockGestPsdAct.getInstance() != null){
            UnlockGestPsdAct.getInstance().finish();
        }
        initData();
        mAdapter.notifyDataSetChanged();



    }
}
