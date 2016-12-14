package com.lq.yl.product.count.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.mdl.ProductMdl;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/10/13.
 */
public class LvTableAdapter extends BaseExpandableListAdapter {

    private ArrayList<List<ProductMdl>> mChildLists;
    private LayoutInflater mInflater;
    private Context mContext;
    private Activity mAct;

    private List<TableProMdl> mGroupLists;

    private DisplayImageOptions mImgOpt;
    private ImageLoader mImgLoader = ImageLoader.getInstance();

    private static LvTableAdapter instance;

    private ViewHolder mViewHolder;
    public LvTableAdapter(Context context, ArrayList<List<ProductMdl>> childLists, List<TableProMdl> lists){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mChildLists = childLists;
        this.mGroupLists = lists;
        this.mImgOpt = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

        instance = this;
    }

    public static LvTableAdapter getInstance(){
        return instance;
    }

    @Override
    public int getGroupCount() {
        return mGroupLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildLists.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupLists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildLists.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.add_tab_pro_lv_item_layout, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        mViewHolder.initView(convertView);

        mViewHolder.initData(mGroupLists.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public class ViewHolder{
        private ImageView mProImg;
        private TextView  mProName;
        private TextView  mProPrice;

        private void initView(View view){
            mProImg = (ImageView) view.findViewById(R.id.proImg);
            mProName = (TextView) view.findViewById(R.id.proName);
            mProPrice = (TextView) view.findViewById(R.id.proPrice);
        }

        private void initData(TableProMdl mdl){
            /*LvTableAdapter.this.mImgLoader.displayImage(mdl.getProPhotoUrl(),mProImg,LvTableAdapter.this.mImgOpt,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                }
            });*/

            mProName.setText(mdl.getTableTitle()+"号桌");
            mProPrice.setText(mdl.getProTotal()+"元");
            mProImg.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }
}