package com.mydesign.modes.newwidgets;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mydesign.modes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NestScrollViewFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;
    private Activity act;

    public NestScrollViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        act = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nest_scroll_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        final GridLayoutManager layoutManager = new GridLayoutManager(act, 6);
        recyclerView.setLayoutManager(layoutManager);
        NestScrollViewAdapter adapter = new NestScrollViewAdapter(null);
        recyclerView.setAdapter(adapter);

       /* recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return false;
            }
        });*/

       recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
           @Override
           public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
               return false;
           }

           @Override
           public void onTouchEvent(RecyclerView rv, MotionEvent e) {

           }

           @Override
           public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

           }
       });

        final int spanCount = layoutManager.getSpanCount();
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                int spanSize = 0;
                if (position == 0 || position == 5) {
                    spanSize = spanCount;
                } else if (position < 5) {
                    spanSize = spanCount / 2;
                } else {
                    spanSize = spanCount / 3;
                }
                return spanSize;
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
