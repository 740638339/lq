package com.lq.yl.product.count.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class LvProSaleDetalAdpt extends BaseAdpt {

    private List<ProductMdl> mList;

    public LvProSaleDetalAdpt(Context context, List<ProductMdl> list){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mList = list;
        this.mImgOpt = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
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
            arg1 = mInflater.inflate(R.layout.pro_sale_detal_item_layout,null);

            viewHolder = new ViewHolder();
            viewHolder.initView(arg1);
            arg1.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) arg1.getTag();
        }

        viewHolder.initData(mList.get(arg0));

        return arg1;
    }


    public class ViewHolder{
        private TextView mProPrice;
        private TextView  mProName;
        private TextView mProNum;
        private LinearLayout mProInfoLL;

        private void initView(View view){
            mProName = (TextView) view.findViewById(R.id.proName);
            mProPrice = (TextView) view.findViewById(R.id.proPrice);
            mProNum = (TextView) view.findViewById(R.id.proNum);
            mProInfoLL = (LinearLayout) view.findViewById(R.id.proInfoLL);
        }

        private void initData(ProductMdl mdl){
            if(mdl.getProNum() != null && !mdl.getProNum().equals("0")){
                mProName.setText(mdl.getProName());
                mProPrice.setText(mdl.getProSalePrice() + "元");
                mProNum.setText(mdl.getProNum() + "份");
            }else{
                mProInfoLL.setVisibility(View.GONE);
            }
        }
    }

}