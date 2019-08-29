package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import java.text.DecimalFormat;
import java.util.List;

public class ShareBikeFeeTipView extends LinearLayout {
    private Button mBtConfirm;
    private ListView mCostSectionListview;
    private TextView mFeeTip;

    public ShareBikeFeeTipView(Context context) {
        this(context, null);
    }

    public ShareBikeFeeTipView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShareBikeFeeTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.share_bike_fee_tip_layout, this, true);
        this.mFeeTip = (TextView) inflate.findViewById(R.id.share_bike_fee_notice_top);
        efx.a(this.mFeeTip);
        this.mBtConfirm = (Button) inflate.findViewById(R.id.share_bike_fee_confirm);
        this.mCostSectionListview = (ListView) inflate.findViewById(R.id.share_bike_cost_section_lv);
        this.mBtConfirm.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ShareBikeFeeTipView.this.setVisibility(8);
            }
        });
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        if (orderDetail == null || !orderDetail.result) {
            setVisibility(8);
            return;
        }
        if (!TextUtils.isEmpty(orderDetail.totalFee)) {
            String format = new DecimalFormat("0.00").format((double) (Float.parseFloat(orderDetail.totalFee) / 100.0f));
            SpannableString spannableString = new SpannableString(format);
            spannableString.setSpan(new AbsoluteSizeSpan(agn.a(getContext(), 39.0f)), 0, format.length() - 1, 33);
            this.mFeeTip.setText(spannableString);
        }
        setCostSection(orderDetail.costSection);
    }

    private void setCostSection(List<egq> list) {
        if (list != null && list.size() != 0) {
            this.mCostSectionListview.setAdapter(new CostSectionAdapter(getContext(), list));
        }
    }
}
