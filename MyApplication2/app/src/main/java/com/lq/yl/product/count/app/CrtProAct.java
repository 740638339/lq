package com.lq.yl.product.count.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.dao.ProductDao;
import com.lq.yl.product.count.app.manager.ProMngAct;
import com.lq.yl.product.count.app.mdl.ProductMdl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrtProAct extends BaseAct{

    private LinearLayout mCrtProLL, mProNameClud, mProOrgPriceClud, mProSalePriceClud, mSpecLL;
    private List<ProductMdl> mList = new ArrayList<ProductMdl>();
    private TextView mSaveTxtV, mProTitleTxtV, mProNameTxtV, mProOrgPriceTxtV,mProSalePriceTxtV, mAddTxtV;
    private EditText mProNameEdt, mProOrgPriceEdt,mProSalePriceEdt;
    private ScrollView mScrolV;
    private ImageView mBackImg;

    private static CrtProAct instance;

    private boolean mIsEdt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crt_pro_act_layout);

        initView();

        initData();
    }

    public void initView() {
        this.mBackImg = (ImageView) this.findViewById(R.id.backImg);
        this.mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrtProAct.this.finish();
            }
        });
        this.mSpecLL = (LinearLayout) this.findViewById(R.id.specLL);
        this.mCrtProLL = (LinearLayout) this.findViewById(R.id.crtProLL);
        this.mScrolV = (ScrollView) this.findViewById(R.id.scrolV);
        this.mScrolV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((InputMethodManager)getSystemService(CrtProAct.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(CrtProAct.this.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        this.mProTitleTxtV = (TextView) this.findViewById(R.id.proTitle);
        this.mAddTxtV = (TextView) this.findViewById(R.id.addTxtV);

        this.mProNameClud = (LinearLayout) findViewById(R.id.proNameClud);
        this.mProNameTxtV = (TextView) this.mProNameClud.findViewById(R.id.lblTxtV);
        this.mProNameEdt = (EditText) this.mProNameClud.findViewById(R.id.contentEdt);


        this.mProOrgPriceClud = (LinearLayout) findViewById(R.id.proOrgPriceClud);
        this.mProOrgPriceTxtV = (TextView) this.mProOrgPriceClud.findViewById(R.id.lblTxtV);
        this.mProOrgPriceEdt = (EditText) this.mProOrgPriceClud.findViewById(R.id.contentEdt);

        this.mProSalePriceClud = (LinearLayout) findViewById(R.id.proSalePriceClud);
        this.mProSalePriceTxtV = (TextView) this.mProSalePriceClud.findViewById(R.id.lblTxtV);
        this.mProSalePriceEdt = (EditText) this.mProSalePriceClud.findViewById(R.id.contentEdt);

        this.mSaveTxtV = (TextView) this.findViewById(R.id.saveTxtV);
    }

    public void initData() {

        this.mIsEdt = getIntent().getBooleanExtra("isEdt", false);

        ProductMdl productMdl = new ProductMdl();

        MyTextWatcher nameWatcher = new MyTextWatcher();
        mProNameEdt.addTextChangedListener(nameWatcher);

        MyTextWatcher orgPriceWatcher = new MyTextWatcher();
        mProOrgPriceEdt.addTextChangedListener(orgPriceWatcher);

        MyTextWatcher salePriceWatcher = new MyTextWatcher();
        mProSalePriceEdt.addTextChangedListener(salePriceWatcher);

        nameWatcher.setMdlAndFun(productMdl, "1");
        orgPriceWatcher.setMdlAndFun(productMdl, "2");
        salePriceWatcher.setMdlAndFun(productMdl, "3");

        mAddTxtV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCrtProLL.addView(getView(null));
            }
        });

        if (this.mIsEdt) {//是否为编辑页面
            mProTitleTxtV.setText("编辑-商品");
            mSpecLL.setVisibility(View.GONE);
            mList = new ProductDao(this).productList();
            for (int i = 0; i < mList.size(); i++) {
                ProductMdl mdl = mList.get(i);
                mCrtProLL.addView(getView(mdl));
            }
        } else {
            mProTitleTxtV.setText("创建-商品");
            mProNameTxtV.setText("商品名称：");
            mProOrgPriceTxtV.setText("商品进价：");
            mProSalePriceTxtV.setText("商品售价：");
            mList.add(productMdl);
        }


        instance = this;

    }

    public View getView(final ProductMdl mdl) {

        String inflater = CrtProAct.this.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) CrtProAct.this.getSystemService(inflater);
        final RelativeLayout linearLayout = (RelativeLayout) layoutInflater.inflate(R.layout.edt_pro_item_layout, null);

        LinearLayout transLL = (LinearLayout) linearLayout.findViewById(R.id.transLL);
        // Add a person
        final ProductMdl productMdl = new ProductMdl();

        ImageView delImg = (ImageView) linearLayout.findViewById(R.id.delImg);

        LinearLayout proClud = (LinearLayout) linearLayout.findViewById(R.id.proClud);
        LinearLayout orgPriceClud = (LinearLayout) linearLayout.findViewById(R.id.orgPriceClud);
        LinearLayout salePriceClud = (LinearLayout) linearLayout.findViewById(R.id.salePriceClud);

        TextView proNameTxtV = (TextView) proClud.findViewById(R.id.lblTxtV);
        final EditText proNameEdt = (EditText) proClud.findViewById(R.id.contentEdt);

        TextView orgPriceTxtV = (TextView) orgPriceClud.findViewById(R.id.lblTxtV);
        final EditText orgPriceEdt = (EditText) orgPriceClud.findViewById(R.id.contentEdt);

        TextView salePriceTxtV = (TextView) salePriceClud.findViewById(R.id.lblTxtV);
        final EditText salePriceEdt = (EditText) salePriceClud.findViewById(R.id.contentEdt);

        MyTextWatcher nameWatcher = new MyTextWatcher();
        MyTextWatcher orgPriceWatcher = new MyTextWatcher();
        MyTextWatcher salePriceWatcher = new MyTextWatcher();

        proNameEdt.addTextChangedListener(nameWatcher);
        orgPriceEdt.addTextChangedListener(orgPriceWatcher);
        salePriceEdt.addTextChangedListener(salePriceWatcher);

        proNameTxtV.setText("商品名称:");
        proNameEdt.setHint("请输入...");

        orgPriceTxtV.setText("商品进价:");
        orgPriceEdt.setHint("请输入...");

        salePriceTxtV.setText("商品售价:");
        salePriceEdt.setHint("请输入...");


        if (mdl != null) {//编辑
            proNameEdt.setText(mdl.getProName());
            orgPriceEdt.setText(mdl.getProOrgPrice());
            salePriceEdt.setText(mdl.getProSalePrice());

            nameWatcher.setMdlAndFun(mdl, "1");
            orgPriceWatcher.setMdlAndFun(mdl, "2");
            salePriceWatcher.setMdlAndFun(mdl, "3");

            delImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(mdl);
                    if (mdl.getID() != null) {
                        ProductDao dao = new ProductDao(CrtProAct.this);
                        if (dao.QueryByIdCount(mdl.getID()) > 0) {
                            dao.del(mdl.getID());
                        }
                    }
                    linearLayout.removeAllViews();
                }
            });
        } else {//创建
            nameWatcher.setMdlAndFun(productMdl, "1");
            orgPriceWatcher.setMdlAndFun(productMdl, "2");
            salePriceWatcher.setMdlAndFun(productMdl, "3");

            productMdl.setIsNewItem(true);//新增的项

            mList.add(productMdl);
            delImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(productMdl);
                    if (productMdl.getID() != null) {
                        ProductDao dao = new ProductDao(CrtProAct.this);
                        if (dao.QueryByIdCount(productMdl.getProName()) > 0) {
                            dao.del(productMdl.getID());
                        }
                    }

                    linearLayout.removeAllViews();
                }
            });

        }

        return linearLayout;
    }

    public void save(View view) {

        boolean mFlag = true;//判断输入框是否为空
        for (int length = 0; length < mList.size(); length++) {
            ProductMdl mdl = mList.get(length);
            if (mdl.getProName() == null || mdl.getProName().equals("")) {
                showToast("请输入商品名称");
                mFlag = false;
            } else if (mdl.getProOrgPrice() == null || mdl.getProOrgPrice().equals("")) {
                showToast("请输入商品进价");
                mFlag = false;
            }else if (mdl.getProSalePrice() == null || mdl.getProSalePrice().equals("")) {
                showToast("请输入商品售价");
                mFlag = false;
            }
        }

        if (mFlag) {
            if (this.mIsEdt) {//判断是否为编辑
                ProductDao dao = new ProductDao(this);
                for (ProductMdl mdl : mList) {
                    if (mdl.isNewItem()) {//新增的项使用 insert
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        mdl.setEnterDate(df.format(new Date()));
                        dao.insert(mdl);
                    } else {
                        dao.update(mdl);
                    }
                }
            } else {
                for (ProductMdl mdl : mList) {
                    new ProductDao(this).insert(mdl);
                }
            }


            if (mList.size() < 1) {//如果list为空，重新添加商品
                startActivity(new Intent(CrtProAct.this, CrtProAct.class));
                this.finish();
            } else if(this.mIsEdt){
                startActivity(new Intent(CrtProAct.this, ProMngAct.class));
                this.finish();
            }else{
                startActivity(new Intent(CrtProAct.this, MainAct.class));
            }
        }
    }

    class MyTextWatcher implements TextWatcher {
        public ProductMdl mMdl;
        public String mFlag;

        public void setMdlAndFun(ProductMdl mMdl, String flag) {
            this.mMdl = mMdl;
            this.mFlag = flag;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {

            if (mMdl != null) {
                if (mFlag.equals("1")) {
                    mMdl.setProName(s + "");
                } else if (mFlag.equals("2")){
                    mMdl.setProOrgPrice(s + "");
                } else if (mFlag.equals("3")){
                    mMdl.setProSalePrice(s + "");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StartAct.getInstance() != null) {
            StartAct.getInstance().finish();
        }

    }

    public static CrtProAct getInstance() {
        return instance;
    }
}