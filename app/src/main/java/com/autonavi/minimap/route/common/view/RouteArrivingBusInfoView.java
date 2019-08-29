package com.autonavi.minimap.route.common.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class RouteArrivingBusInfoView extends RelativeLayout {
    private static final int BUS_STOP_FONT_SIZE = 11;
    private static final String EMPTY_TIME_INFO_TEXT = "— —";
    private String BUS_STOP_TEXT;
    private ImageView mRealtimeIcon;
    private TextView mStationInfo;
    private TextView mTimeInfo;

    public RouteArrivingBusInfoView(Context context) {
        super(context);
    }

    public RouteArrivingBusInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteArrivingBusInfoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.BUS_STOP_TEXT = getResources().getString(R.string.bus_stop);
        this.mRealtimeIcon = (ImageView) findViewById(R.id.realtime_icon);
        this.mStationInfo = (TextView) findViewById(R.id.station_info);
        this.mTimeInfo = (TextView) findViewById(R.id.time_info);
    }

    public void showData(int i, int i2) {
        int a = ebj.a(i, i2);
        if (a == 0 || a == 1) {
            setAnimationDrawable(this.mRealtimeIcon, false, 0);
            this.mRealtimeIcon.setVisibility(8);
            this.mStationInfo.setText(ebj.a(getContext(), 0, i, i2));
            this.mStationInfo.setVisibility(0);
            this.mTimeInfo.setText(EMPTY_TIME_INFO_TEXT);
            this.mTimeInfo.setVisibility(8);
            return;
        }
        setAnimationDrawable(this.mRealtimeIcon, true, R.anim.realtime_busline_blue_gif);
        this.mRealtimeIcon.setVisibility(0);
        StringBuilder sb = new StringBuilder();
        sb.append(i2 + 1);
        sb.append(this.BUS_STOP_TEXT);
        SpannableString spannableString = new SpannableString(sb.toString());
        spannableString.setSpan(new AbsoluteSizeSpan(11, true), spannableString.length() - this.BUS_STOP_TEXT.length(), spannableString.length(), 33);
        this.mStationInfo.setText(spannableString);
        this.mStationInfo.setVisibility(0);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.route_about));
        sb2.append(axt.a(i, true));
        this.mTimeInfo.setText(sb2.toString());
        this.mTimeInfo.setVisibility(0);
    }

    public void showNoData() {
        setAnimationDrawable(this.mRealtimeIcon, false, 0);
        this.mRealtimeIcon.setVisibility(8);
        this.mStationInfo.setVisibility(8);
        this.mTimeInfo.setText(EMPTY_TIME_INFO_TEXT);
        this.mTimeInfo.setVisibility(0);
    }

    public void showDataByStatus(int i) {
        setAnimationDrawable(this.mRealtimeIcon, false, 0);
        this.mRealtimeIcon.setVisibility(8);
        if (i == 0) {
            this.mStationInfo.setVisibility(8);
            this.mTimeInfo.setVisibility(0);
            this.mTimeInfo.setText(EMPTY_TIME_INFO_TEXT);
        } else if (i == 3) {
            this.mStationInfo.setVisibility(8);
            this.mTimeInfo.setVisibility(0);
            this.mTimeInfo.setText(getContext().getString(R.string.real_time_bus_no_time));
        } else {
            if (i == 2) {
                this.mTimeInfo.setVisibility(8);
                this.mStationInfo.setVisibility(0);
                this.mStationInfo.setText(getContext().getString(R.string.real_time_bus_no_bus));
            }
        }
    }

    private void setAnimationDrawable(@NonNull ImageView imageView, boolean z, int i) {
        Drawable drawable = imageView.getDrawable();
        AnimationDrawable animationDrawable = drawable instanceof AnimationDrawable ? (AnimationDrawable) drawable : null;
        if (z) {
            if (animationDrawable == null) {
                Drawable drawable2 = getResources().getDrawable(i);
                if (drawable2 instanceof AnimationDrawable) {
                    animationDrawable = (AnimationDrawable) drawable2;
                    imageView.setImageDrawable(drawable2);
                }
            }
            if (animationDrawable != null && !animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        } else {
            if (animationDrawable != null && animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
            if (i > 0) {
                imageView.setImageResource(i);
            }
        }
    }
}
