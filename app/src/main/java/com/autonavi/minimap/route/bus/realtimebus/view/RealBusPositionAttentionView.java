package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

public class RealBusPositionAttentionView extends RelativeLayout {
    private View mAttentionNoRealTimeBusInfoLayout;
    private View mAttentionRealTimeBusInfoLayout;
    private Context mContext;
    public PullToRefreshListView mFollowRealTimeBusListView;
    private a mListener;
    private TextView mSearchBusStopView;

    public interface a {
        void d();
    }

    public RealBusPositionAttentionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.realbus_position_attention, this, true);
        this.mAttentionNoRealTimeBusInfoLayout = findViewById(R.id.attention_show_no_realtimebus_info_layout);
        this.mSearchBusStopView = (TextView) findViewById(R.id.tv_rt_follow_default_search);
        this.mAttentionRealTimeBusInfoLayout = findViewById(R.id.attention_show_realtimebus_info_layout);
        this.mFollowRealTimeBusListView = (PullToRefreshListView) findViewById(R.id.attention_ls_realtimebus_position);
        this.mSearchBusStopView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RealBusPositionAttentionView.this.onSearchButtonClick();
            }
        });
    }

    /* access modifiers changed from: private */
    public void onSearchButtonClick() {
        if (this.mListener != null) {
            this.mListener.d();
        }
    }

    public void toggleAttentionView(boolean z, boolean z2) {
        if (z) {
            this.mAttentionNoRealTimeBusInfoLayout.setVisibility(8);
            this.mAttentionRealTimeBusInfoLayout.setVisibility(0);
            return;
        }
        this.mAttentionNoRealTimeBusInfoLayout.setVisibility(0);
        this.mAttentionRealTimeBusInfoLayout.setVisibility(8);
        if (z2) {
            this.mSearchBusStopView.setVisibility(0);
        } else {
            this.mSearchBusStopView.setVisibility(8);
        }
    }

    public void onAttentionRefresh() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.real_time_refresh_time));
        getContext();
        sb.append(dxx.a());
        String sb2 = sb.toString();
        this.mFollowRealTimeBusListView.setHeaderText(sb2, this.mContext.getString(R.string.bus_real_time_release_to_refesh), sb2);
    }

    public void setListener(a aVar) {
        this.mListener = aVar;
    }
}
