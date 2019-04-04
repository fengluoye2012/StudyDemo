package com.mydesign.modes.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

    private List<T> dataList;
    protected Context context;

    AbstractBaseAdapter(Context context, List<T> list) {
        this.dataList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return (dataList != null && position < dataList.size() && position >= 0) ? dataList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateAdapter(List<T> list) {
        if (list == null) {
            return;
        }

        if (dataList == null) {
            dataList = list;
        } else {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (dataList != null) {
            dataList.clear();
        }
    }

    protected View getInflate(int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public List<T> getList() {
        return dataList;
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }
}
