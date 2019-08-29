package com.autonavi.bundle.routecommute.bus.overlay.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.map.widget.StrokeTextView;
import com.autonavi.minimap.R;

public class StationDescOverlayView extends RelativeLayout {
    private TextView mBusLineInfo;
    private StrokeTextView mEtaInfo;
    private StrokeTextView mStationTitle;

    public StationDescOverlayView(Context context) {
        super(context);
        initView(context);
    }

    public StationDescOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_drive_station_desc_overlay, this, true);
        this.mStationTitle = (StrokeTextView) findViewById(R.id.drive_station_desc_title);
        this.mStationTitle.setStrokeWidth(agn.a(getContext(), 2.0f));
        this.mBusLineInfo = (TextView) findViewById(R.id.drive_station_info);
        this.mEtaInfo = (StrokeTextView) findViewById(R.id.drive_station_info_eta_info);
        this.mEtaInfo.setTextColor(-11239681);
        this.mEtaInfo.setStrokeWidth(agn.a(getContext(), 2.0f));
    }

    public void initRealTimeInfo(RealTimeBusLineInfo realTimeBusLineInfo) {
        setEtaText(ayt.a(realTimeBusLineInfo));
    }

    public void setBusLineText(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > 7) {
                StringBuilder sb = new StringBuilder();
                sb.append(str.substring(0, 7));
                sb.append("...");
                str = sb.toString();
            }
            this.mBusLineInfo.setText(str);
        }
    }

    public void setEtaText(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mEtaInfo.setText(str);
        }
    }

    public void setStationTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mStationTitle.setText(str);
        }
    }

    public GradientDrawable getBackgroundDrawable(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius((float) agn.a(getContext(), 7.0f));
        return gradientDrawable;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initData(com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0055
            r0 = 0
            r1 = 0
            if (r5 == 0) goto L_0x0023
            java.util.ArrayList<com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath$BusSegment> r2 = r5.segmentlist
            if (r2 == 0) goto L_0x0023
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0023
            java.lang.Object r2 = r2.get(r1)
            com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath$BusSegment r2 = (com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment) r2
            if (r2 == 0) goto L_0x0023
            java.lang.String r3 = r2.startname
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0023
            java.lang.String r2 = r2.startname
            goto L_0x0024
        L_0x0023:
            r2 = r0
        L_0x0024:
            r4.setStationTitle(r2)
            if (r5 == 0) goto L_0x0045
            java.util.ArrayList<com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath$BusSegment> r2 = r5.segmentlist
            if (r2 == 0) goto L_0x0045
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0045
            java.lang.Object r1 = r2.get(r1)
            com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath$BusSegment r1 = (com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment) r1
            if (r1 == 0) goto L_0x0045
            java.lang.String r2 = r1.bus_key_name
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0045
            java.lang.String r0 = r1.bus_key_name
        L_0x0045:
            r4.setBusLineText(r0)
            android.widget.TextView r0 = r4.mBusLineInfo
            int r5 = defpackage.ayt.a(r5)
            android.graphics.drawable.GradientDrawable r5 = r4.getBackgroundDrawable(r5)
            r0.setBackgroundDrawable(r5)
        L_0x0055:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommute.bus.overlay.view.StationDescOverlayView.initData(com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath):void");
    }
}
