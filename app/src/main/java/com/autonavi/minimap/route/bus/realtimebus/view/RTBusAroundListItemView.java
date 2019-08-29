package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusAroundAdapter;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;

public class RTBusAroundListItemView extends RTBusAroundListItemBaseView {
    /* access modifiers changed from: private */
    public boolean isStared;
    private ImageView iv_progress;
    private RelativeLayout rt_bus_info;
    private View rt_bus_position_info_layout;
    private ImageView rt_bus_star;
    private View rt_bus_star_click_area;
    private View rt_bus_star_spliter;
    private ImageView rt_bus_status_error;
    private TextView rt_bus_status_tips_bold;
    private TextView rt_bus_status_tips_distance;
    private TextView rt_bus_status_tips_nodata;
    private TextView rt_bus_status_tips_normal;
    private TextView rt_bus_status_tips_time;

    public RTBusAroundListItemView(Context context) {
        this(context, null);
    }

    public RTBusAroundListItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RTBusAroundListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isStared = false;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.rt_bus_info = (RelativeLayout) findViewById(R.id.rt_bus_info);
        this.rt_bus_position_info_layout = findViewById(R.id.rt_bus_position_info_layout);
        this.rt_bus_status_tips_distance = (TextView) findViewById(R.id.rt_bus_status_tips_distance);
        this.rt_bus_status_tips_time = (TextView) findViewById(R.id.rt_bus_status_tips_time);
        this.rt_bus_status_tips_bold = (TextView) findViewById(R.id.rt_bus_status_tips_bold);
        this.rt_bus_status_tips_normal = (TextView) findViewById(R.id.rt_bus_status_tips_normal);
        this.rt_bus_status_error = (ImageView) findViewById(R.id.rt_bus_status_error);
        this.rt_bus_status_tips_nodata = (TextView) findViewById(R.id.rt_bus_status_tips_nodata);
        this.rt_bus_star_click_area = findViewById(R.id.rt_bus_star_click_area);
        this.rt_bus_star = (ImageView) findViewById(R.id.rt_bus_star);
        this.rt_bus_star_spliter = findViewById(R.id.rt_bus_star_spliter);
        this.iv_progress = (ImageView) findViewById(R.id.iv_progress);
    }

    private void setRTBusInfoText(String str, String str2, String str3, String str4, boolean z) {
        int i = 0;
        this.rt_bus_status_tips_distance.setVisibility(TextUtils.isEmpty(str) ? 8 : 0);
        this.rt_bus_status_tips_time.setVisibility(TextUtils.isEmpty(str2) ? 8 : 0);
        this.rt_bus_status_tips_bold.setVisibility(TextUtils.isEmpty(str3) ? 8 : 0);
        TextView textView = this.rt_bus_status_tips_normal;
        if (TextUtils.isEmpty(str4)) {
            i = 8;
        }
        textView.setVisibility(i);
        this.rt_bus_status_tips_distance.setText(str);
        this.rt_bus_status_tips_time.setText(str2);
        this.rt_bus_status_tips_bold.setText(str3);
        this.rt_bus_status_tips_normal.setText(str4);
        if (z) {
            dxx.a(getContext(), this.iv_progress);
        } else {
            dxx.a(this.iv_progress);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c2, code lost:
        r2 = null;
        r3 = null;
        r5 = null;
        r4 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c7, code lost:
        r4 = null;
        r5 = null;
        r3 = r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setRTBusPositionInfo(boolean r10, int r11) {
        /*
            r9 = this;
            android.content.Context r0 = r9.getContext()
            android.view.View r1 = r9.rt_bus_position_info_layout
            r2 = 0
            r3 = 8
            if (r10 == 0) goto L_0x000d
            r4 = 0
            goto L_0x000f
        L_0x000d:
            r4 = 8
        L_0x000f:
            r1.setVisibility(r4)
            android.view.View r1 = r9.rt_bus_star_spliter
            if (r10 == 0) goto L_0x0018
            r4 = 0
            goto L_0x001a
        L_0x0018:
            r4 = 8
        L_0x001a:
            r1.setVisibility(r4)
            android.view.View r1 = r9.rt_bus_star_click_area
            if (r10 == 0) goto L_0x0023
            r4 = 0
            goto L_0x0025
        L_0x0023:
            r4 = 8
        L_0x0025:
            r1.setVisibility(r4)
            android.widget.TextView r1 = r9.rt_bus_status_tips_normal
            r1.setVisibility(r3)
            if (r10 != 0) goto L_0x0030
            return
        L_0x0030:
            r10 = 2
            if (r11 < r10) goto L_0x0042
            android.widget.ImageView r10 = r9.rt_bus_status_error
            r10.setVisibility(r2)
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r9
            r3.setRTBusInfoText(r4, r5, r6, r7, r8)
            return
        L_0x0042:
            android.widget.ImageView r10 = r9.rt_bus_status_error
            r10.setVisibility(r3)
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r10 = r9.mData
            boolean r10 = r10.isLoadFinish()
            r11 = 1
            r6 = r10 ^ 1
            r10 = 0
            if (r6 != 0) goto L_0x00e1
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r1 = r9.mData
            boolean r1 = r1.isHasData()
            if (r1 != 0) goto L_0x0067
            int r11 = com.autonavi.minimap.R.string.real_time_bus_no_data
            java.lang.String r11 = r0.getString(r11)
        L_0x0061:
            r2 = r10
            r3 = r2
            r4 = r3
            r5 = r11
            goto L_0x00e5
        L_0x0067:
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r1 = r9.mData
            boolean r1 = r1.isBusComming()
            if (r1 == 0) goto L_0x00cb
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r1 = r9.mData
            com.autonavi.bundle.routecommon.entity.Trip r1 = r1.mTrip
            int r2 = r1.arrival
            int r3 = r1.station_left
            int r2 = defpackage.ebj.a(r2, r3)
            switch(r2) {
                case 0: goto L_0x00bc;
                case 1: goto L_0x00b5;
                case 2: goto L_0x0081;
                default: goto L_0x007e;
            }
        L_0x007e:
            r11 = r10
            r2 = r11
            goto L_0x00c7
        L_0x0081:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r3 = r1.station_left
            int r3 = r3 + r11
            r2.append(r3)
            int r3 = com.autonavi.minimap.R.string.bus_stop
            java.lang.String r3 = r0.getString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            int r4 = com.autonavi.minimap.R.string.autonavi_search_result_about
            java.lang.String r0 = r0.getString(r4)
            r3.append(r0)
            int r0 = r1.arrival
            java.lang.String r11 = defpackage.axt.a(r0, r11)
            r3.append(r11)
            java.lang.String r11 = r3.toString()
            goto L_0x00c7
        L_0x00b5:
            int r11 = com.autonavi.minimap.R.string.about_to_arriving_in_station
            java.lang.String r11 = r0.getString(r11)
            goto L_0x00c2
        L_0x00bc:
            int r11 = com.autonavi.minimap.R.string.arrived_in_station
            java.lang.String r11 = r0.getString(r11)
        L_0x00c2:
            r2 = r10
            r3 = r2
            r5 = r3
            r4 = r11
            goto L_0x00e5
        L_0x00c7:
            r4 = r10
            r5 = r4
            r3 = r11
            goto L_0x00e5
        L_0x00cb:
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup r11 = r9.mData
            boolean r11 = r11.isBusStop()
            if (r11 == 0) goto L_0x00da
            int r11 = com.autonavi.minimap.R.string.real_time_bus_no_time
            java.lang.String r11 = r0.getString(r11)
            goto L_0x0061
        L_0x00da:
            int r11 = com.autonavi.minimap.R.string.real_time_bus_no_bus
            java.lang.String r11 = r0.getString(r11)
            goto L_0x0061
        L_0x00e1:
            r2 = r10
            r3 = r2
            r4 = r3
            r5 = r4
        L_0x00e5:
            r1 = r9
            r1.setRTBusInfoText(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.view.RTBusAroundListItemView.setRTBusPositionInfo(boolean, int):void");
    }

    public void bindData(int i, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, Bundle bundle) {
        if (realTimeBusAndStationMatchup != null) {
            super.bindData(i, realTimeBusAndStationMatchup, bundle);
            boolean isRTBusData = isRTBusData();
            this.rt_bus_star_click_area.setOnClickListener(null);
            if (isRTBusData) {
                this.isStared = this.mData.isFollow();
                this.rt_bus_star.setImageDrawable(getContext().getResources().getDrawable(this.isStared ? R.drawable.rt_bus_star_select : R.drawable.rt_bus_star_unselect));
                NoDBClickUtil.a(this.rt_bus_star_click_area, (OnClickListener) new OnClickListener() {
                    public final void onClick(View view) {
                        if (RTBusAroundListItemView.this.mClickListener != null) {
                            RTBusAroundListItemView.this.mClickListener.onViewClicked(RTBusAroundListItemView.this.mPositionId, RTBusAroundListItemView.this.isStared ? ViewClickAction.RT_BUS_VIEW_UNSTAR : ViewClickAction.RT_BUS_VIEW_STAR, RTBusAroundListItemView.this.mData, view);
                        }
                    }
                });
            }
            int i2 = 0;
            this.rt_bus_status_tips_nodata.setVisibility(isRTBusData ? 8 : 0);
            if (isRTBusData) {
                ((LayoutParams) this.rt_bus_info.getLayoutParams()).addRule(0, R.id.rt_bus_position_info_layout);
            } else {
                ((LayoutParams) this.rt_bus_info.getLayoutParams()).addRule(0, R.id.rt_bus_status_tips_nodata);
            }
            if (bundle != null) {
                i2 = bundle.getInt(RTBusAroundAdapter.EXT_RT_BUS_RETRY_TIMES, 0);
            }
            setRTBusPositionInfo(isRTBusData, i2);
        }
    }
}
