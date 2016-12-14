package com.lq.yl.product.count.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lq.yl.product.count.R;
import com.lq.yl.product.count.app.mdl.TableProMdl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by wb-liuquan.e on 2016/11/14.
 */
public class LvDilaryAdpt extends BaseAdpt {

    private List<TableProMdl> mList;

    private static LvDilaryAdpt instance;

    public LvDilaryAdpt(Context context, List<TableProMdl> lists){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mList = lists;
        this.mImgOpt = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

        instance = this;
    }

    public static LvDilaryAdpt getInstance(){
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
            arg1 = mInflater.inflate(R.layout.dilary_order_lv_item,null);

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
        private TextView tab_title,start_time,end_time,total;

        private void initView(View view){
            tab_title = (TextView) view.findViewById(R.id.tab_title);
            start_time = (TextView) view.findViewById(R.id.start_time);
            end_time = (TextView) view.findViewById(R.id.end_time);
            total = (TextView) view.findViewById(R.id.total);
        }

        private void initData(TableProMdl mdl){
            this.tab_title.setText(mdl.getTableTitle());
            this.start_time.setText(mdl.getStartTime());
            this.end_time.setText(mdl.getEndTime());
            this.total.setText(mdl.getProTotal());
        }
    }
}
