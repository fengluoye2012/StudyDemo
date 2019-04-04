package com.mydesign.modes.newwidgets;


import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mydesign.modes.R;
import com.mydesign.modes.utils.CommonUtils;

import java.util.List;

public class NestScrollViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean setData;

    private SideSlipDeleteView mSideSlipDeleteView;

    public NestScrollViewAdapter(List<String> list) {
        super(R.layout.item_scroll, list);
        setData = false;
    }


    @Override
    protected void convert(BaseViewHolder helper, String item, int pos) {
        helper.setText(R.id.textView, "我是条目：" + pos);

        View textView = helper.getView(R.id.textView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = CommonUtils.dipToPixels((int) (Math.random() * (50)) + 50);

        ((SideSlipDeleteView) helper.getView(R.id.slipDeleteView)).setSlipDeleteStateChangeListener(new SideSlipDeleteView.OnSlipDeleteStateChangeListener() {
            @Override
            public void slipDeleteOpen(SideSlipDeleteView sideSlipDeleteView) {
                mSideSlipDeleteView = sideSlipDeleteView;
            }

            @Override
            public void slipDeleteClose(SideSlipDeleteView sideSlipDeleteView) {
                mSideSlipDeleteView = null;
            }

            @Override
            public void slipDeleteMove(SideSlipDeleteView sideSlipDeleteView) {
                if (mSideSlipDeleteView != null && mSideSlipDeleteView != sideSlipDeleteView) {
                    mSideSlipDeleteView.closeSlipDelete();
                }
            }
        });
    }

}
