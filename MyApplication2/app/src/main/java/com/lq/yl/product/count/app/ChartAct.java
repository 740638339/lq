package com.lq.yl.product.count.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.adapter.SpinerAdapter;
import com.lq.yl.product.count.app.dao.OrderDao;
import com.lq.yl.product.count.app.mdl.OrderMdl;
import com.lq.yl.product.count.app.other.gest.UnlockGestPsdAct;
import com.lq.yl.product.count.app.util.DateUtils;
import com.lq.yl.product.count.app.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

public class ChartAct extends BaseAct implements View.OnClickListener, SpinerAdapter.OnItemSelectListener {

    public static String LOCK_PSD_CHART_CODE = "LOCK_PSD_CHART_CODE";

    private ImageView mBackImg;
    private BarChart mBarChart;
    private BarData mBarData;

    private TextView mChartTitleTxtV;
    private LinearLayout mBtDropDown;
    private String mTimeSpace = "";

    private List<String> mListType = new ArrayList<String>();  //类型列表
    private TextView mTView;
    private SpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(mTView.getWidth());
        mSpinerPopWindow.showAsDropDown(mTView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_act_layout);

        this.mBackImg = (ImageView) this.findViewById(R.id.backImg);
        this.mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartAct.this.finish();
            }
        });

        this.mTView = (TextView) findViewById(R.id.tv_value);
        this.mBtDropDown = (LinearLayout) this.findViewById(R.id.bt_dropdown);

        this.mBtDropDown.setOnClickListener(this);
        this.mChartTitleTxtV = (TextView) findViewById(R.id.chartTitleTxtV);
        this.mBarChart = (BarChart) findViewById(R.id.spread_bar_chart);

        final String[] arr = getResources().getStringArray(R.array.time_space);
        for (int i = 0; i < arr.length; i++) {
            mListType.add(arr[i]);
        }

        this.mAdapter = new SpinerAdapter(this, mListType);
        this.mAdapter.refreshData(mListType, 0);

        //显示第一条数据
        //mTView.setText(names[0]);

        //初始化PopWindow
        this.mSpinerPopWindow = new SpinerPopWindow(this);
        this.mSpinerPopWindow.setAdatper(mAdapter);
        this.mSpinerPopWindow.setItemListener(this);

    }

    @Override
    public void onItemClick(int pos) {
        // TODO Auto-generated method stub
        mTimeSpace = mListType.get(pos);

        mChartTitleTxtV.setVisibility(View.VISIBLE);
        mChartTitleTxtV.setText(mTimeSpace + "收入情况");
        if (pos >= 0 && pos <= mListType.size()) {
            String value = mListType.get(pos);
            mTView.setText(value.toString());
        }


        if (mTimeSpace.equals(mListType.get(0))) {
            ArrayList<OrderMdl> list = new OrderDao(ChartAct.this).getIntervalList(DateUtils.getCrtDate(), DateUtils.getStateTime(7));
            mBarData = getBarData(list, 12, 100);
        } else if (mTimeSpace.equals(mListType.get(1))) {
            ArrayList<OrderMdl> list = new OrderDao(ChartAct.this).getIntervalList(DateUtils.getCrtDate(), DateUtils.getStateTime(30));
            mBarData = getBarData(list, 12, 100);
        } else if (mTimeSpace.equals(mListType.get(2))) {
            ArrayList<OrderMdl> list = new OrderDao(ChartAct.this).getIntervalList(DateUtils.getCrtDate(), DateUtils.getStateTime(365));
            mBarData = getBarData(list, 12, 100);
        }
        showBarChart(mBarChart, mBarData);

    }


    private void showBarChart(BarChart barChart, BarData barData) {
        barChart.setDrawBorders(true);  ////是否在折线图上添加边框

        barChart.setDescriptionColor(R.color.white);
        barChart.setDescription(mTimeSpace + "总收入：" + new OrderDao(ChartAct.this).getIntervalTotal(DateUtils.getCrtDate(), DateUtils.getStateTime(7)));// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        //barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//      barChart.setBackgroundColor();// 设置背景

        barChart.setDrawBarShadow(true);

        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.animateX(2500); // 立即执行的动画,x轴
    }

    private BarData getBarData(ArrayList<OrderMdl> lists, int xMax, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        //xValues.add(0 + "");
        for (int i = 0; i < xMax; i++) {
            xValues.add(i + "");
        }
        xValues.add(xMax + "");
        // y轴的数据
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        for (int i = 0; i < lists.size(); i++) {
            OrderMdl mdl = lists.get(i);
            if (i == 0) {
                yValues.add(new BarEntry(Float.parseFloat(mdl.getRevenueTotal()), i + 1));
                continue;
            }
            yValues.add(new BarEntry(Float.parseFloat(mdl.getRevenueTotal()), i + 1));
            /*float value = (float) (Math.random() * 20) + 3;
            yValues.add(new Entry(value, i));*/
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试饼状图");

        barDataSet.setColor(getResources().getColor(R.color.an_lanse));
        barDataSet.setBarSpacePercent(40f);

        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);

        return barData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dropdown:
                showSpinWindow();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(UnlockGestPsdAct.getInstance() != null){
            UnlockGestPsdAct.getInstance().finish();
        }
    }
}
