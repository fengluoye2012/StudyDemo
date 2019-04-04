package com.mydesign.modes.base;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class HolderBaseAdapter<T,H> extends AbstractBaseAdapter<T> {

    public HolderBaseAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H viewHolder;
        if (convertView == null) {
            convertView = buildConvertView();
            viewHolder = buildHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (H) convertView.getTag();
        }
        bindViewsData(position, viewHolder);
        return convertView;
    }

    /**
     * 创建ConvertView
     *
     * @return
     */
    protected abstract View buildConvertView();

    /**
     * 创建ViewHolder
     *
     * @param convertView
     * @return
     */
    protected abstract H buildHolder(View convertView);


    /**
     * 填充数据
     *
     * @param position
     * @param viewHolder
     */
    protected abstract void bindViewsData(int position, H viewHolder);

}
