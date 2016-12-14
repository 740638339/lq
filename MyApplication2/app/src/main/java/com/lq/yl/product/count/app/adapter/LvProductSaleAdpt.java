package com.lq.yl.product.count.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class LvProductSaleAdpt extends BaseAdpt {


    private List<ProductMdl> mList;

    private Map<String,String> mMap;
    private static LvProductSaleAdpt instance;
    public LvProductSaleAdpt(Context context, List<ProductMdl> list){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mList = list;
        this.mImgOpt = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

        instance = this;

    }

    public List<ProductMdl> getList() {
        return mList;
    }

    public static LvProductSaleAdpt getInstance(){
        return instance;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.mList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if(arg1 == null){
            arg1 = mInflater.inflate(R.layout.pro_sale_item_layout,null);

            viewHolder = new ViewHolder();
            viewHolder.initView(arg1);
            arg1.setTag(viewHolder);

            viewHolder.initData(mList.get(arg0));
        }

        return arg1;
    }


    public class ViewHolder{
        private TextView mProPrice;
        private TextView  mProName;
        private EditText mProNum;
        private ImageView mProJia;
        private ImageView mProJian;
        private ProductMdl mMdl;

        private void initView(View view){
            mProName = (TextView) view.findViewById(R.id.proName);
            mProPrice = (TextView) view.findViewById(R.id.proPrice);
            mProNum = (EditText) view.findViewById(R.id.proNum);
            mProJia = (ImageView) view.findViewById(R.id.jiaNumBtn);
            mProJian = (ImageView) view.findViewById(R.id.jianNumBtn);

            mProJia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mProNum.setText((Integer.parseInt(mProNum.getText().toString()) + 1)+"");
                    ViewHolder.this.mMdl.setProNum(mProNum.getText().toString());
                }
            });

            mProJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(mProNum.getText().toString()) > 0){
                        mProNum.setText((Integer.parseInt(mProNum.getText().toString()) - 1)+"");
                        ViewHolder.this.mMdl.setProNum(mProNum.getText().toString());
                    }
                }
            });
        }

        private void initData(ProductMdl mdl){
            MyTextWatcher textWatcher = new MyTextWatcher();
            textWatcher.setMdl(mdl);

            mProNum.addTextChangedListener(textWatcher);

            mProNum.setText(mdl.getProNum() == null ? "0" : mdl.getProNum());
            mProNum.setFocusable(true);
            mProNum.setFocusableInTouchMode(true);

            mProName.setText(mdl.getProName());
            mProPrice.setText(mdl.getProSalePrice() + "元");

            this.mMdl = mdl;
        }

    }


    public Map<String,String> getAllProNumMap(){
        mMap = new HashMap<String,String>();
        for (int i = 0; i < mList.size(); i++){
            ProductMdl mdl = mList.get(i);
            mMap.put(mdl.getProName(),mdl.getProNum());
        }
        return mMap;
    }

    public double[] getTotal(){
        double total[] = new double[]{0,0};
        for (int i = 0; i < mList.size(); i++){
            ProductMdl mdl = mList.get(i);
            if(mdl.getProNum() != null){
                total[0] += Double.parseDouble(mdl.getProSalePrice()) * Integer.parseInt(mdl.getProNum());
                total[1] += Double.parseDouble(mdl.getProOrgPrice()) * Integer.parseInt(mdl.getProNum());
            }
        }

        return total;
    }


    class MyTextWatcher implements TextWatcher {
        public ProductMdl mMdl;

        public void setMdl(ProductMdl mMdl) {
            this.mMdl = mMdl;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {

            mMdl.setProNum(s + "");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}