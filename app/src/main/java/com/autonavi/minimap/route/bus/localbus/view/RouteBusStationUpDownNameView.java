package com.autonavi.minimap.route.bus.localbus.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class RouteBusStationUpDownNameView extends RelativeLayout {
    private int POINT_STROKE_WIDTH;
    public ImageView mEndPointView;
    public TextView mEntranceNameView;
    private View mPointView;
    public TextView mStationNameView;

    public RouteBusStationUpDownNameView(Context context) {
        super(context);
    }

    public RouteBusStationUpDownNameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteBusStationUpDownNameView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.POINT_STROKE_WIDTH = agn.a(getContext(), 3.0f) / 2;
        this.mPointView = findViewById(R.id.point);
        this.mEndPointView = (ImageView) findViewById(R.id.endpoint);
        this.mStationNameView = (TextView) findViewById(R.id.station_name);
        this.mEntranceNameView = (TextView) findViewById(R.id.subway_entrance);
    }

    private void setStationNameView(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mStationNameView.setText(str);
        }
    }

    private void setEntranceNameView(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            this.mEntranceNameView.setVisibility(8);
            return;
        }
        this.mEntranceNameView.setText(str);
        dwn.a(this.mEntranceNameView, i);
        this.mEntranceNameView.setVisibility(0);
    }

    private void setPointView(int i) {
        dwn.a(this.mPointView, this.POINT_STROKE_WIDTH, i);
        this.mPointView.setVisibility(0);
        this.mEndPointView.setVisibility(8);
    }

    private void setEndPointView(boolean z, int i) {
        int i2;
        if (z) {
            i2 = R.drawable.route_bus_result_up_station_icon;
        } else {
            i2 = R.drawable.route_bus_result_down_station_icon;
        }
        this.mEndPointView.setImageResource(i2);
        dwn.a(this.mEndPointView, i);
        this.mEndPointView.setVisibility(0);
        this.mPointView.setVisibility(8);
    }

    public void setStationData(String str, String str2, int i, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            setStationNameView(str);
            setEntranceNameView(str2, i);
            setEndPointView(z, i);
        }
    }
}
