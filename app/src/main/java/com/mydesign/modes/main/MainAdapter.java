package com.mydesign.modes.main;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mydesign.modes.R;
import com.mydesign.modes.base.HolderBaseAdapter;
import com.mydesign.modes.newwidgets.SideSlipDeleteView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainAdapter extends HolderBaseAdapter<String, MainAdapter.MainViewHolder> {


    private SideSlipDeleteView mSideSlipDeleteView;

    public MainAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected View buildConvertView() {
        return getInflate(R.layout.item_main);
    }

    @Override
    protected MainViewHolder buildHolder(View convertView) {
        return new MainViewHolder(convertView);
    }

    protected void bindViewsData(final int position, final MainViewHolder viewHolder) {
        String item = getItem(position);
        viewHolder.textView.setText(item);

        viewHolder.sideSlipDeleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.sideSlipDeleteView.closeSlipDelete();
                if (onSlipDeleteViewItemClick != null) {
                    onSlipDeleteViewItemClick.onItemClick(position);
                }
            }
        });

        viewHolder.tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteViewClick != null) {
                    onDeleteViewClick.onDeleteViewClick(position);
                }
            }
        });

        viewHolder.sideSlipDeleteView.setSlipDeleteStateChangeListener(new SideSlipDeleteView.OnSlipDeleteStateChangeListener() {
            @Override
            public void slipDeleteOpen(SideSlipDeleteView sideSlipDeleteView) {
                mSideSlipDeleteView = sideSlipDeleteView;
            }

            @Override
            public void slipDeleteClose(SideSlipDeleteView sideSlipDeleteView) {
                if (mSideSlipDeleteView == sideSlipDeleteView) {
                    mSideSlipDeleteView = null;
                }
            }

            @Override
            public void slipDeleteMove(SideSlipDeleteView sideSlipDeleteView) {
                if (mSideSlipDeleteView != null && mSideSlipDeleteView != sideSlipDeleteView) {
                    mSideSlipDeleteView.closeSlipDelete();
                }
            }
        });
    }

    static class MainViewHolder {
        @BindView(R.id.textView)
        TextView textView;

        @BindView(R.id.tv_right)
        TextView tv_right;

        @BindView(R.id.slipDeleteView)
        SideSlipDeleteView sideSlipDeleteView;

        MainViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //替代ListView条目点击；
    interface OnSlipDeleteViewItemClick {
        void onItemClick(int pos);
    }

    private OnSlipDeleteViewItemClick onSlipDeleteViewItemClick;

    void setOnSlipDeleteViewItemClickListener(OnSlipDeleteViewItemClick onSlipDeleteViewItemClick) {
        this.onSlipDeleteViewItemClick = onSlipDeleteViewItemClick;
    }

    //删除的点击事件
    interface OnDeleteViewClick {
        void onDeleteViewClick(int pos);
    }

    private OnDeleteViewClick onDeleteViewClick;

    void setOnDeleteViewClickListener(OnDeleteViewClick onDeleteViewClick) {
        this.onDeleteViewClick = onDeleteViewClick;
    }
}
