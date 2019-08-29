package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.widget.FlowLayout;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView;

public class BusLineDetailItemView extends LinearLayout implements dxy<dyh> {
    private RelativeLayout busline_station;
    private RouteArrivingBusInfoView[] mArrivingBusInfoViewArray;
    private TextView mArrivingBusView;
    private int mBlueColor;
    private View mBuslineDetailInfoContainer;
    private ImageView mBuslineDetailInfoExpand;
    protected dxz mClickListener;
    protected dyh mData;
    private int mGrayColor;
    private ImageView mLineView;
    protected int mPositionId;
    private View mRefreshView;
    private FlowLayout mStationContainer;
    private ImageView mStationIcon;
    private TextView mStationNameView;
    private String mTagNear;
    private int normal_height = 0;

    public boolean isDataChanged(dyh dyh) {
        return true;
    }

    public BusLineDetailItemView(Context context) {
        super(context);
    }

    public BusLineDetailItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BusLineDetailItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.normal_height = agn.a(getContext(), 20.0f);
        this.mGrayColor = getResources().getColor(R.color.f_c_3);
        this.mBlueColor = getResources().getColor(R.color.f_c_6);
        this.mTagNear = getResources().getString(R.string.busline_close_to_me);
        this.busline_station = (RelativeLayout) findViewById(R.id.busline_station);
        NoDBClickUtil.a((View) this.busline_station, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (BusLineDetailItemView.this.mClickListener != null) {
                    BusLineDetailItemView.this.mClickListener.onViewClicked(BusLineDetailItemView.this.mPositionId, ViewClickAction.BUS_LINE_DETAIL_ITEM_CLICK, BusLineDetailItemView.this.mData, view);
                }
            }
        });
        this.mStationIcon = (ImageView) findViewById(R.id.busline_detail_station_icon);
        this.mLineView = (ImageView) findViewById(R.id.busline_detail_line_icon);
        this.mRefreshView = findViewById(R.id.busline_detail_refresh);
        NoDBClickUtil.a(this.mRefreshView, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (BusLineDetailItemView.this.mClickListener != null) {
                    BusLineDetailItemView.this.mClickListener.onViewClicked(BusLineDetailItemView.this.mPositionId, ViewClickAction.BUS_LINE_DETAIL_REFRESH_RT_DATA, BusLineDetailItemView.this.mData, view);
                }
            }
        });
        this.mArrivingBusView = (TextView) findViewById(R.id.busline_detail_arriving_bus);
        this.mArrivingBusInfoViewArray = new RouteArrivingBusInfoView[3];
        this.mArrivingBusInfoViewArray[0] = (RouteArrivingBusInfoView) findViewById(R.id.arriving_bus_info_item_1);
        this.mArrivingBusInfoViewArray[1] = (RouteArrivingBusInfoView) findViewById(R.id.arriving_bus_info_item_2);
        this.mArrivingBusInfoViewArray[2] = (RouteArrivingBusInfoView) findViewById(R.id.arriving_bus_info_item_3);
        this.mStationNameView = (TextView) findViewById(R.id.busline_detail_station_name);
        this.mStationContainer = (FlowLayout) findViewById(R.id.busline_detail_station_container);
        this.mBuslineDetailInfoExpand = (ImageView) findViewById(R.id.busline_detail_expand_icon);
        NoDBClickUtil.a((View) this.mBuslineDetailInfoExpand, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (BusLineDetailItemView.this.mClickListener != null) {
                    BusLineDetailItemView.this.mClickListener.onViewClicked(BusLineDetailItemView.this.mPositionId, ViewClickAction.BUS_LINE_DETAIL_EXPEND_RT_DATA, BusLineDetailItemView.this.mData, view);
                }
            }
        });
        this.mBuslineDetailInfoContainer = findViewById(R.id.busline_detail_info_container);
        findViewById(R.id.real_time_arriving_bus_details).setBackgroundResource(R.drawable.busline_detail_info_blue_bg);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x012a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bindData(int r18, defpackage.dyh r19, android.os.Bundle r20) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r1.mPositionId = r2
            r1.mData = r3
            java.lang.String r5 = r3.c
            java.lang.String r6 = "bus_line_near_1km_station_name"
            r7 = 0
            java.lang.String r6 = r4.getString(r6, r7)
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L_0x0023
            android.widget.ImageView r8 = r1.mStationIcon
            int r9 = com.autonavi.minimap.R.drawable.direction_bus_list_green
            r8.setImageResource(r9)
            goto L_0x002a
        L_0x0023:
            android.widget.ImageView r8 = r1.mStationIcon
            int r9 = com.autonavi.minimap.R.drawable.direction_bus_list_blue
            r8.setImageResource(r9)
        L_0x002a:
            android.text.SpannableStringBuilder r8 = new android.text.SpannableStringBuilder
            r8.<init>(r5)
            if (r6 == 0) goto L_0x0036
            java.lang.String r9 = r1.mTagNear
            r8.append(r9)
        L_0x0036:
            int r3 = r3.f
            r9 = 33
            r10 = 0
            if (r3 != 0) goto L_0x0059
            android.content.res.Resources r3 = r17.getResources()
            int r6 = com.autonavi.minimap.R.string.busline_temp_outage
            java.lang.String r3 = r3.getString(r6)
            r8.append(r3)
            android.text.style.ForegroundColorSpan r3 = new android.text.style.ForegroundColorSpan
            int r6 = r1.mGrayColor
            r3.<init>(r6)
            int r6 = r8.length()
            r8.setSpan(r3, r10, r6, r9)
            goto L_0x0093
        L_0x0059:
            r11 = 3
            if (r3 != r11) goto L_0x0078
            android.content.res.Resources r3 = r17.getResources()
            int r6 = com.autonavi.minimap.R.string.busline_defer_open
            java.lang.String r3 = r3.getString(r6)
            r8.append(r3)
            android.text.style.ForegroundColorSpan r3 = new android.text.style.ForegroundColorSpan
            int r6 = r1.mGrayColor
            r3.<init>(r6)
            int r6 = r8.length()
            r8.setSpan(r3, r10, r6, r9)
            goto L_0x0093
        L_0x0078:
            if (r6 == 0) goto L_0x0093
            int r3 = r5.length()
            int r6 = r5.length()
            java.lang.String r11 = r1.mTagNear
            int r11 = r11.length()
            int r6 = r6 + r11
            android.text.style.ForegroundColorSpan r11 = new android.text.style.ForegroundColorSpan
            int r12 = r1.mBlueColor
            r11.<init>(r12)
            r8.setSpan(r11, r3, r6, r9)
        L_0x0093:
            java.lang.String r3 = "bus_line_expend_index"
            r6 = -1
            int r3 = r4.getInt(r3, r6)
            r11 = 1
            if (r2 != r3) goto L_0x00a9
            android.text.style.StyleSpan r12 = new android.text.style.StyleSpan
            r12.<init>(r11)
            int r13 = r8.length()
            r8.setSpan(r12, r10, r13, r9)
        L_0x00a9:
            android.widget.TextView r9 = r1.mStationNameView
            r9.setText(r8)
            dyh r8 = r1.mData
            java.lang.String r8 = r8.d
            dyh r9 = r1.mData
            com.autonavi.minimap.route.bus.model.Bus r9 = r9.a
            com.autonavi.minimap.route.bus.model.BusSubwayInfo r9 = r9.subwayInfo
            java.util.ArrayList r5 = r9.getSubwayInfoByName(r5, r8)
            com.autonavi.map.widget.FlowLayout r8 = r1.mStationContainer
            r8.removeAllViews()
            com.autonavi.map.widget.FlowLayout r8 = r1.mStationContainer
            android.widget.TextView r9 = r1.mStationNameView
            r8.addView(r9)
            if (r5 == 0) goto L_0x0150
            int r8 = r5.size()
            if (r8 <= 0) goto L_0x0150
            int r8 = r5.size()
            r9 = 0
        L_0x00d5:
            if (r9 >= r8) goto L_0x0150
            java.lang.Object r12 = r5.get(r9)
            com.autonavi.minimap.route.bus.model.BusSubwayStation r12 = (com.autonavi.minimap.route.bus.model.BusSubwayStation) r12
            java.lang.String r13 = r12.subwayName
            boolean r13 = android.text.TextUtils.isEmpty(r13)
            if (r13 != 0) goto L_0x014b
            android.content.Context r13 = r17.getContext()
            android.view.LayoutInflater r13 = android.view.LayoutInflater.from(r13)
            int r14 = com.autonavi.minimap.R.layout.busline_station_subwayinfo
            android.view.View r13 = r13.inflate(r14, r7)
            int r14 = com.autonavi.minimap.R.id.subway_name_tv
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            java.lang.String r15 = r12.subwayName
            r14.setText(r15)
            java.lang.String r7 = r12.subwayColor
            if (r7 == 0) goto L_0x011e
            java.lang.String r7 = r12.subwayColor
            java.lang.String r7 = r7.trim()
            java.lang.String r15 = "#"
            boolean r7 = r7.equals(r15)
            if (r7 != 0) goto L_0x011e
            java.lang.String r7 = r12.subwayColor     // Catch:{ Exception -> 0x0119 }
            int r15 = android.graphics.Color.parseColor(r7)     // Catch:{ Exception -> 0x0119 }
            goto L_0x0120
        L_0x0119:
            r0 = move-exception
            r7 = r0
            r7.printStackTrace()
        L_0x011e:
            r15 = -16777216(0xffffffffff000000, float:-1.7014118E38)
        L_0x0120:
            int r7 = android.os.Build.VERSION.SDK_INT
            r12 = 15
            if (r7 >= r12) goto L_0x012a
            r14.setBackgroundColor(r15)
            goto L_0x0146
        L_0x012a:
            android.graphics.drawable.GradientDrawable r7 = new android.graphics.drawable.GradientDrawable
            r7.<init>()
            r7.setShape(r10)
            android.content.Context r12 = r17.getContext()
            r6 = 1094713344(0x41400000, float:12.0)
            int r6 = defpackage.agn.a(r12, r6)
            float r6 = (float) r6
            r7.setCornerRadius(r6)
            r7.setColor(r15)
            r14.setBackgroundDrawable(r7)
        L_0x0146:
            com.autonavi.map.widget.FlowLayout r6 = r1.mStationContainer
            r6.addView(r13)
        L_0x014b:
            int r9 = r9 + 1
            r6 = -1
            r7 = 0
            goto L_0x00d5
        L_0x0150:
            java.lang.String r5 = "rt_bus_detail_error_times"
            int r5 = r4.getInt(r5, r10)
            dyh r6 = r1.mData
            com.autonavi.minimap.route.bus.model.Bus r6 = r6.a
            boolean r6 = r6.isRealTime
            if (r6 == 0) goto L_0x0172
            boolean r5 = defpackage.ebj.a(r5)
            if (r5 == 0) goto L_0x0172
            dyh r5 = r1.mData
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline r5 = r5.h
            if (r5 == 0) goto L_0x0172
            dyh r5 = r1.mData
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline r5 = r5.h
            java.util.List<com.autonavi.bundle.routecommon.entity.Trip> r7 = r5.tripList
            goto L_0x0173
        L_0x0172:
            r7 = 0
        L_0x0173:
            java.lang.String r5 = "bus_line_detai_param_from_favorite"
            boolean r5 = r4.getBoolean(r5, r10)
            dyh r6 = r1.mData
            com.autonavi.minimap.route.bus.model.Bus r6 = r6.a
            boolean r6 = r6.isRealTime
            r8 = 8
            if (r6 == 0) goto L_0x01ee
            if (r5 == 0) goto L_0x0186
            goto L_0x01ee
        L_0x0186:
            android.widget.ImageView r5 = r1.mBuslineDetailInfoExpand
            java.lang.Integer r6 = java.lang.Integer.valueOf(r18)
            r5.setTag(r6)
            android.widget.ImageView r5 = r1.mBuslineDetailInfoExpand
            r5.setVisibility(r10)
            if (r2 == r3) goto L_0x01a3
            android.widget.ImageView r3 = r1.mBuslineDetailInfoExpand
            int r4 = com.autonavi.minimap.R.drawable.route_arrow_down
            r3.setImageResource(r4)
            android.view.View r3 = r1.mBuslineDetailInfoContainer
            r3.setVisibility(r8)
            goto L_0x01f8
        L_0x01a3:
            android.widget.ImageView r3 = r1.mBuslineDetailInfoExpand
            int r5 = com.autonavi.minimap.R.drawable.route_arrow_up
            r3.setImageResource(r5)
            android.view.View r3 = r1.mBuslineDetailInfoContainer
            r3.setVisibility(r10)
            java.lang.String r3 = "rt_bus_detail_loading"
            boolean r3 = r4.getBoolean(r3, r10)
            if (r3 == 0) goto L_0x01be
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r3 = r1.mArrivingBusInfoViewArray
            defpackage.ebj.a(r3, r10)
            goto L_0x01f8
        L_0x01be:
            dyh r3 = r1.mData
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline r3 = r3.h
            if (r3 == 0) goto L_0x01f8
            dyh r3 = r1.mData
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline r3 = r3.h
            int r3 = r3.status
            if (r11 != r3) goto L_0x01e3
            if (r7 == 0) goto L_0x01f8
            int r3 = r7.size()
            if (r3 <= 0) goto L_0x01f8
            android.widget.TextView r3 = r1.mArrivingBusView
            int r4 = r7.size()
            defpackage.ebj.a(r3, r4)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r3 = r1.mArrivingBusInfoViewArray
            defpackage.ebj.a(r3, r7)
            goto L_0x01f8
        L_0x01e3:
            android.widget.TextView r4 = r1.mArrivingBusView
            defpackage.ebj.a(r4, r10)
            com.autonavi.minimap.route.common.view.RouteArrivingBusInfoView[] r4 = r1.mArrivingBusInfoViewArray
            defpackage.ebj.a(r4, r3)
            goto L_0x01f8
        L_0x01ee:
            android.widget.ImageView r3 = r1.mBuslineDetailInfoExpand
            r3.setVisibility(r8)
            android.view.View r3 = r1.mBuslineDetailInfoContainer
            r3.setVisibility(r8)
        L_0x01f8:
            android.widget.ImageView r3 = r1.mLineView
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            if (r2 != 0) goto L_0x020b
            r4 = -1
            r3.height = r4
            int r2 = r1.normal_height
            r3.setMargins(r10, r2, r10, r10)
            goto L_0x0222
        L_0x020b:
            dyh r4 = r1.mData
            com.autonavi.minimap.route.bus.model.Bus r4 = r4.a
            java.lang.String[] r4 = r4.stations
            int r4 = r4.length
            int r4 = r4 - r11
            if (r2 != r4) goto L_0x021c
            int r2 = r1.normal_height
            int r2 = r2 + 10
            r3.height = r2
            goto L_0x0222
        L_0x021c:
            r2 = -1
            r3.height = r2
            r3.setMargins(r10, r10, r10, r10)
        L_0x0222:
            android.widget.ImageView r2 = r1.mLineView
            r2.setLayoutParams(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.view.BusLineDetailItemView.bindData(int, dyh, android.os.Bundle):void");
    }

    public dyh getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
    }
}
