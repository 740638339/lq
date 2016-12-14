package com.lq.yl.product.count.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/11.
 */
public class LvProductInfoAdpt extends BaseAdpt {

    protected List<ProductMdl> mList;


    public LvProductInfoAdpt(Context context, List<ProductMdl> list){
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
            arg1 = mInflater.inflate(R.layout.product_info_lv_item_layout,null);

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
        private ImageView mProImg;
        private TextView  mProName;
        private TextView  mProOrgPrice,mProSalePrice;

        private void initView(View view){
            mProImg = (ImageView) view.findViewById(R.id.proImg);
            mProName = (TextView) view.findViewById(R.id.txtV1);
            mProOrgPrice = (TextView) view.findViewById(R.id.txtV2);
            mProSalePrice = (TextView) view.findViewById(R.id.txtV3);
        }

        private void initData(ProductMdl mdl){
            /*LvProductInfoAdpt.this.mImgLoader.displayImage(mdl.getProPhotoUrl(),mProImg,LvProductInfoAdpt.this.mImgOpt,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                }
            });*/

            mProName.setText(mdl.getProName());
            mProOrgPrice.setText("进价：" + mdl.getProOrgPrice()+" 元");
            mProSalePrice.setText("售价：" + mdl.getProSalePrice()+" 元");
            mProImg.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }
}