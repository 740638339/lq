package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.LvProSaleDetalAdpt;
import com.lq.yl.product.count.app.adapter.LvProductSaleAdpt;
import com.lq.yl.product.count.app.dao.OrderDao;
import com.lq.yl.product.count.app.dao.StoreProDao;
import com.lq.yl.product.count.app.dao.TabProDao;
import com.lq.yl.product.count.app.mdl.OrderMdl;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.lq.yl.product.count.app.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/13.
 */
public class BillDetailAct extends BaseAct {


    private List<ProductMdl> mList = new ArrayList<>();
    private ListView mProdctSaleLv;
    private LvProSaleDetalAdpt mAdapter;

    private TextView mTotalTxtV, mCloseTxtV, mWelcomeTxtV;
    private ImageView mBackImg;
    private TableProMdl mTableProMdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_detal_layout);

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
                BillDetailAct.this.finish();
            }
        });
    }

    public void initData() {

        mTableProMdl = (TableProMdl) getIntent().getSerializableExtra("tableProMdl");

        if (mTableProMdl != null) {
            for (ProductMdl productMdl : mTableProMdl.getProList()) {
                if (productMdl.getProNum() != null && !productMdl.getProNum().equals("0")) {
                    mList.add(productMdl);
                }
            }

            mAdapter = new LvProSaleDetalAdpt(this, mList);

            mProdctSaleLv.setAdapter(mAdapter);

            mTotalTxtV.setText("总计消费：" + LvProductSaleAdpt.getInstance().getTotal()[0] + "元");

            mWelcomeTxtV.setText("欢迎下次光临" + new StoreProDao(this).listAll().get(0).getStoreName());
            mCloseTxtV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCloseTxtV.setEnabled(false);
                    mTableProMdl.setEndTime(DateUtils.getCrtTime());
                    mTableProMdl.setIsPay("1");
                    new TabProDao(BillDetailAct.this).update(mTableProMdl);

                    OrderDao dao = new OrderDao(BillDetailAct.this);
                    OrderMdl orderMdl = dao.queryMdlByDate(DateUtils.getCrtDate());

                    double crtActRevTol = Double.parseDouble(mTableProMdl.getProRevenue());
                    double crtRevTol = Double.parseDouble(mTableProMdl.getProTotal());
                    ArrayList<TableProMdl> list;
                    if (orderMdl != null) {
                        orderMdl.setActRevenueTotal((Double.parseDouble(orderMdl.getActRevenueTotal()) + crtActRevTol) + "");
                        orderMdl.setRevenueTotal((Double.parseDouble(orderMdl.getRevenueTotal()) + crtRevTol) + "");
                        list = orderMdl.getTableList();
                        list.add(mTableProMdl);//把当前的桌位，存进历史订单
                        dao.update(orderMdl);
                    } else {
                        orderMdl = new OrderMdl();

                        orderMdl.setActRevenueTotal(crtActRevTol + "");
                        orderMdl.setRevenueTotal(crtRevTol + "");

                        Calendar calendar = Calendar.getInstance();
                        orderMdl.setYear(calendar.get(Calendar.YEAR) + "");
                        orderMdl.setMonth((calendar.get(Calendar.MONTH) + 1) + "");
                        orderMdl.setDay(calendar.get(Calendar.DAY_OF_MONTH) + "");

                        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                            orderMdl.setWeekDay(7 + "");
                        } else {
                            orderMdl.setWeekDay((calendar.get(Calendar.DAY_OF_WEEK) - 1) + "");
                        }

                        orderMdl.setCreateDate(DateUtils.getCrtDate());

                        list = new ArrayList<TableProMdl>();
                        list.add(mTableProMdl);
                        orderMdl.setTableList(list);

                        dao.insert(orderMdl);

                    }

                    //new TabProDao(BillDetailAct.this).del(mTableProMdl);

                    Intent intent = new Intent(BillDetailAct.this, ListTableAct.class);
                    startActivity(intent);
                    BillDetailAct.this.finish();
                }
            });
        }
    }
}
