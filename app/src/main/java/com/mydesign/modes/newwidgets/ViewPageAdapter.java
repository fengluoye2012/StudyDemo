package com.mydesign.modes.newwidgets;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mydesign.modes.R;
import com.mydesign.modes.glidetest.GlideTest;

import java.util.List;


public class ViewPageAdapter extends PagerAdapter {
    List<String> list;
    List<String> titleList;

    public ViewPageAdapter(List<String> list, List<String> titleList) {
        super();
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item, null);
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(container.getContext(), "huhuhu0", Toast.LENGTH_SHORT).show();
            }
        });
        String url = list.get(position);
        GlideTest.loadImageView( url, imageView,R.mipmap.ic_launcher);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
