package com.lq.yl.product.count.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wb-liuquan.e on 2016/11/18.
 */
public class BaseAdpt extends BaseAdapter {

    public LayoutInflater mInflater;
    public Context mContext;
    public Activity mAct;
    public DisplayImageOptions mImgOpt;
    public ImageLoader mImgLoader = ImageLoader.getInstance();

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
