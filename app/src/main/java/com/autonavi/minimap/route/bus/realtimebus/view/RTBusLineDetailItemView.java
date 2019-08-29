package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.map.widget.FlowLayout;
import com.autonavi.minimap.R;

public class RTBusLineDetailItemView extends LinearLayout implements dxy<dyh> {
    private ImageView addAttentionButton;
    private TextView alreadyAttentTextView;
    private ImageView imageViewLine;
    private int mBlueColor;
    protected dxz mClickListener;
    protected dyh mData;
    private int mGrayColor;
    private LayoutInflater mLayoutInflater;
    protected int mPositionId;
    private ImageView mStationIcon;
    private TextView mStationNameView;
    private int normal_height = 0;
    private RelativeLayout realbusContent;
    private FlowLayout station_collect;

    public boolean isDataChanged(dyh dyh) {
        return true;
    }

    public RTBusLineDetailItemView(Context context) {
        super(context);
    }

    public RTBusLineDetailItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RTBusLineDetailItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLayoutInflater = LayoutInflater.from(getContext());
        this.normal_height = agn.a(getContext(), 28.0f);
        this.mGrayColor = getResources().getColor(R.color.f_c_3);
        this.mBlueColor = getResources().getColor(R.color.f_c_6);
        this.mStationIcon = (ImageView) findViewById(R.id.busline_detail_station_icon);
        this.mStationNameView = (TextView) findViewById(R.id.busline_detail_station_name);
        this.imageViewLine = (ImageView) findViewById(R.id.busline_detail_line_icon);
        this.station_collect = (FlowLayout) findViewById(R.id.busline_detail_station_container);
        this.addAttentionButton = (ImageView) findViewById(R.id.busline_detail_add_attention);
        this.realbusContent = (RelativeLayout) findViewById(R.id.realbus_item_detail_content);
        this.alreadyAttentTextView = (TextView) findViewById(R.id.already_attention_text);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x018f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void bindData(int r11, defpackage.dyh r12, android.os.Bundle r13) {
        /*
            r10 = this;
            r10.mPositionId = r11
            r10.mData = r12
            java.lang.String r11 = "bus_line_sel_item_index"
            r12 = -1
            int r11 = r13.getInt(r11, r12)
            dyh r0 = r10.mData
            int r0 = r0.b
            r1 = 1
            r2 = 0
            if (r11 != r0) goto L_0x0015
            r11 = 1
            goto L_0x0016
        L_0x0015:
            r11 = 0
        L_0x0016:
            dyh r0 = r10.mData
            boolean r0 = r0.g
            r3 = 8
            if (r0 == 0) goto L_0x0029
            android.widget.TextView r11 = r10.alreadyAttentTextView
            r11.setVisibility(r2)
            android.widget.ImageView r11 = r10.addAttentionButton
            r11.setVisibility(r3)
            goto L_0x004e
        L_0x0029:
            android.widget.ImageView r0 = r10.addAttentionButton
            r0.setVisibility(r2)
            if (r11 == 0) goto L_0x0038
            android.widget.ImageView r0 = r10.addAttentionButton
            int r4 = com.autonavi.minimap.R.drawable.radio_btn_on
            r0.setImageResource(r4)
            goto L_0x003f
        L_0x0038:
            android.widget.ImageView r0 = r10.addAttentionButton
            int r4 = com.autonavi.minimap.R.drawable.radio_btn_off
            r0.setImageResource(r4)
        L_0x003f:
            android.widget.RelativeLayout r0 = r10.realbusContent
            com.autonavi.minimap.route.bus.realtimebus.view.RTBusLineDetailItemView$1 r4 = new com.autonavi.minimap.route.bus.realtimebus.view.RTBusLineDetailItemView$1
            r4.<init>(r11)
            r0.setOnClickListener(r4)
            android.widget.TextView r11 = r10.alreadyAttentTextView
            r11.setVisibility(r3)
        L_0x004e:
            dyh r11 = r10.mData
            java.lang.String r11 = r11.c
            dyh r0 = r10.mData
            java.lang.String r0 = r0.d
            android.content.res.Resources r3 = r10.getResources()
            int r4 = com.autonavi.minimap.R.string.busline_close_to_me
            java.lang.String r3 = r3.getString(r4)
            java.lang.String r4 = "rt_bus_line_near_1km_station_name"
            r5 = 0
            java.lang.String r13 = r13.getString(r4, r5)
            boolean r13 = r11.equals(r13)
            r4 = 33
            if (r13 == 0) goto L_0x009e
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r11)
            r13.append(r3)
            java.lang.String r13 = r13.toString()
            android.text.SpannableString r3 = new android.text.SpannableString
            r3.<init>(r13)
            android.text.style.ForegroundColorSpan r6 = new android.text.style.ForegroundColorSpan
            int r7 = r10.mBlueColor
            r6.<init>(r7)
            int r7 = r11.length()
            int r8 = r3.length()
            r3.setSpan(r6, r7, r8, r4)
            android.widget.ImageView r6 = r10.mStationIcon
            int r7 = com.autonavi.minimap.R.drawable.direction_bus_list_green
            r6.setBackgroundResource(r7)
            goto L_0x00ab
        L_0x009e:
            android.text.SpannableString r3 = new android.text.SpannableString
            r3.<init>(r11)
            android.widget.ImageView r13 = r10.mStationIcon
            int r6 = com.autonavi.minimap.R.drawable.direction_bus_list_blue
            r13.setBackgroundResource(r6)
            r13 = r11
        L_0x00ab:
            dyh r6 = r10.mData
            int r6 = r6.f
            if (r6 != 0) goto L_0x00de
            android.text.SpannableString r3 = new android.text.SpannableString
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r13)
            android.content.res.Resources r13 = r10.getResources()
            int r7 = com.autonavi.minimap.R.string.busline_temp_outage
            java.lang.String r13 = r13.getString(r7)
            r6.append(r13)
            java.lang.String r13 = r6.toString()
            r3.<init>(r13)
            android.text.style.ForegroundColorSpan r13 = new android.text.style.ForegroundColorSpan
            int r6 = r10.mGrayColor
            r13.<init>(r6)
            int r6 = r3.length()
            r3.setSpan(r13, r2, r6, r4)
            goto L_0x010d
        L_0x00de:
            r7 = 3
            if (r6 != r7) goto L_0x010d
            android.text.SpannableString r3 = new android.text.SpannableString
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r13)
            android.content.res.Resources r13 = r10.getResources()
            int r7 = com.autonavi.minimap.R.string.busline_defer_open
            java.lang.String r13 = r13.getString(r7)
            r6.append(r13)
            java.lang.String r13 = r6.toString()
            r3.<init>(r13)
            android.text.style.ForegroundColorSpan r13 = new android.text.style.ForegroundColorSpan
            int r6 = r10.mGrayColor
            r13.<init>(r6)
            int r6 = r3.length()
            r3.setSpan(r13, r2, r6, r4)
        L_0x010d:
            android.widget.TextView r13 = r10.mStationNameView
            r13.setText(r3)
            dyh r13 = r10.mData
            com.autonavi.minimap.route.bus.model.Bus r13 = r13.a
            com.autonavi.minimap.route.bus.model.BusSubwayInfo r13 = r13.subwayInfo
            if (r13 == 0) goto L_0x0125
            dyh r13 = r10.mData
            com.autonavi.minimap.route.bus.model.Bus r13 = r13.a
            com.autonavi.minimap.route.bus.model.BusSubwayInfo r13 = r13.subwayInfo
            java.util.ArrayList r11 = r13.getSubwayInfoByName(r11, r0)
            goto L_0x0126
        L_0x0125:
            r11 = r5
        L_0x0126:
            com.autonavi.map.widget.FlowLayout r13 = r10.station_collect
            r13.removeAllViews()
            com.autonavi.map.widget.FlowLayout r13 = r10.station_collect
            android.widget.TextView r0 = r10.mStationNameView
            r13.addView(r0)
            if (r11 == 0) goto L_0x01b3
            int r13 = r11.size()
            if (r13 <= 0) goto L_0x01b3
            int r13 = r11.size()
            r0 = 0
        L_0x013f:
            if (r0 >= r13) goto L_0x01b3
            java.lang.Object r3 = r11.get(r0)
            com.autonavi.minimap.route.bus.model.BusSubwayStation r3 = (com.autonavi.minimap.route.bus.model.BusSubwayStation) r3
            java.lang.String r4 = r3.subwayName
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x01b0
            android.view.LayoutInflater r4 = r10.mLayoutInflater
            int r6 = com.autonavi.minimap.R.layout.busline_station_subwayinfo
            android.view.View r4 = r4.inflate(r6, r5)
            int r6 = com.autonavi.minimap.R.id.subway_name_tv
            android.view.View r6 = r4.findViewById(r6)
            android.widget.TextView r6 = (android.widget.TextView) r6
            java.lang.String r7 = r3.subwayName
            r6.setText(r7)
            r7 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            java.lang.String r8 = r3.subwayColor
            if (r8 == 0) goto L_0x0183
            java.lang.String r8 = r3.subwayColor
            java.lang.String r8 = r8.trim()
            java.lang.String r9 = "#"
            boolean r8 = r8.equals(r9)
            if (r8 != 0) goto L_0x0183
            java.lang.String r3 = r3.subwayColor     // Catch:{ Exception -> 0x017f }
            int r3 = android.graphics.Color.parseColor(r3)     // Catch:{ Exception -> 0x017f }
            goto L_0x0185
        L_0x017f:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0183:
            r3 = -16777216(0xffffffffff000000, float:-1.7014118E38)
        L_0x0185:
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 15
            if (r7 >= r8) goto L_0x018f
            r6.setBackgroundColor(r3)
            goto L_0x01ab
        L_0x018f:
            android.graphics.drawable.GradientDrawable r7 = new android.graphics.drawable.GradientDrawable
            r7.<init>()
            r7.setShape(r2)
            android.content.Context r8 = r10.getContext()
            r9 = 1094713344(0x41400000, float:12.0)
            int r8 = defpackage.agn.a(r8, r9)
            float r8 = (float) r8
            r7.setCornerRadius(r8)
            r7.setColor(r3)
            r6.setBackgroundDrawable(r7)
        L_0x01ab:
            com.autonavi.map.widget.FlowLayout r3 = r10.station_collect
            r3.addView(r4)
        L_0x01b0:
            int r0 = r0 + 1
            goto L_0x013f
        L_0x01b3:
            android.widget.ImageView r11 = r10.imageViewLine
            android.view.ViewGroup$LayoutParams r11 = r11.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r11 = (android.widget.FrameLayout.LayoutParams) r11
            dyh r13 = r10.mData
            int r13 = r13.b
            if (r13 != 0) goto L_0x01ca
            int r12 = r10.normal_height
            r11.height = r12
            r12 = 81
            r11.gravity = r12
            goto L_0x01e7
        L_0x01ca:
            dyh r13 = r10.mData
            int r13 = r13.b
            dyh r0 = r10.mData
            com.autonavi.minimap.route.bus.model.Bus r0 = r0.a
            java.lang.String[] r0 = r0.stations
            int r0 = r0.length
            int r0 = r0 - r1
            if (r13 != r0) goto L_0x01e1
            int r12 = r10.normal_height
            r11.height = r12
            r12 = 49
            r11.gravity = r12
            goto L_0x01e7
        L_0x01e1:
            r11.height = r12
            r12 = 17
            r11.gravity = r12
        L_0x01e7:
            android.widget.ImageView r12 = r10.imageViewLine
            r12.setLayoutParams(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.view.RTBusLineDetailItemView.bindData(int, dyh, android.os.Bundle):void");
    }

    public dyh getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
    }
}
