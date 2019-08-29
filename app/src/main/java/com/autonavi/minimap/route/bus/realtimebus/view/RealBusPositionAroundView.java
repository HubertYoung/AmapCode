package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

public class RealBusPositionAroundView extends RelativeLayout {
    public static final String SP_KEY_realbus_position_bluetip_show = "realbus_position_bluetip_show";
    private View mAroundNoRealTimeBusInfoLayout;
    private View mAroundRealTimeBusInfoLayout;
    public PullToRefreshListView mAroundRealTimeBusPositionList;
    private ImageView mAroundTopTipClose;
    public RelativeLayout mAroundTopTipLayout;
    private Context mContext;
    /* access modifiers changed from: private */
    public MapSharePreference mapsharePreference = new MapSharePreference(SharePreferenceName.user_route_method_info);

    public RealBusPositionAroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.realbus_position_around, this, true);
        this.mAroundNoRealTimeBusInfoLayout = findViewById(R.id.around_show_no_realtimebus_info_layout);
        this.mAroundRealTimeBusInfoLayout = findViewById(R.id.around_show_realtimebus_info_layout);
        this.mAroundRealTimeBusPositionList = (PullToRefreshListView) findViewById(R.id.around_ls_realtimebus_position);
        this.mAroundTopTipLayout = (RelativeLayout) findViewById(R.id.realbus_position_around_top_tip);
        this.mAroundTopTipClose = (ImageView) findViewById(R.id.realbus_top_tip_close);
        this.mAroundTopTipClose.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RealBusPositionAroundView.this.mAroundTopTipLayout.setVisibility(8);
                RealBusPositionAroundView.this.mapsharePreference.putBooleanValue(RealBusPositionAroundView.SP_KEY_realbus_position_bluetip_show, false);
            }
        });
    }

    public void toggleAroundView(boolean z) {
        if (z) {
            this.mAroundNoRealTimeBusInfoLayout.setVisibility(8);
            this.mAroundRealTimeBusInfoLayout.setVisibility(0);
            return;
        }
        this.mAroundNoRealTimeBusInfoLayout.setVisibility(0);
        this.mAroundRealTimeBusInfoLayout.setVisibility(8);
    }

    public void onAroundRefresh() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getString(R.string.real_time_refresh_time));
        getContext();
        sb.append(dxx.a());
        String sb2 = sb.toString();
        this.mAroundRealTimeBusPositionList.setHeaderText(sb2, this.mContext.getString(R.string.bus_real_time_release_to_refesh), sb2);
    }
}
