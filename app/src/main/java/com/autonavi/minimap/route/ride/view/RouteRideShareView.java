package com.autonavi.minimap.route.ride.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;

public class RouteRideShareView extends LinearLayout {
    private TextView mFootTitleFrom;
    private TextView mFootTitleTo;
    private ImageView mIvMapBg = ((ImageView) findViewById(R.id.iv_map_bg));
    private View mLineDivision;
    private LinearLayout mRainBowFlagView;
    private TextView mRunCarlor;
    private TextView mRunDistance;
    private TextView mRunSpeed;
    private TextView mRunTime;
    private TextView mShareTime;

    public RouteRideShareView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.route_ride_finsh_share_view, this, true);
        LayoutParams layoutParams = new LayoutParams(ags.a(getContext()).width(), ags.a(getContext()).height());
        this.mIvMapBg.setLayoutParams(layoutParams);
        initView();
    }

    private void initView() {
        this.mRunDistance = (TextView) findViewById(R.id.run_distance);
        this.mRunTime = (TextView) findViewById(R.id.run_time_share);
        this.mRunCarlor = (TextView) findViewById(R.id.run_carlor);
        this.mRunSpeed = (TextView) findViewById(R.id.run_speed);
        efx.a(this.mRunTime);
        efx.a(this.mRunDistance);
        efx.a(this.mRunSpeed);
        efx.a(this.mRunCarlor);
        this.mShareTime = (TextView) findViewById(R.id.run_share_time);
        paintStrokeTextView(this.mShareTime);
        this.mFootTitleFrom = (TextView) findViewById(R.id.route_title_foot_from);
        this.mFootTitleTo = (TextView) findViewById(R.id.route_title_foot_to);
        paintStrokeTextView(this.mFootTitleFrom);
        paintStrokeTextView(this.mFootTitleTo);
        this.mRainBowFlagView = (LinearLayout) findViewById(R.id.rainbow_flag);
        this.mLineDivision = findViewById(R.id.line_division);
    }

    public void bindData(RideTraceHistory rideTraceHistory) {
        if (rideTraceHistory != null) {
            setRunTimeView(rideTraceHistory.b);
            if (rideTraceHistory.e >= rideTraceHistory.f) {
                rideTraceHistory.f = rideTraceHistory.e * 1.12d;
            }
            setMaxSpeedView(rideTraceHistory.f);
            setDistanceView(rideTraceHistory.c);
            setRideAverageSpeedView(rideTraceHistory.e);
            String str = rideTraceHistory.l;
            String str2 = rideTraceHistory.m;
            if (!(rideTraceHistory.j == null || rideTraceHistory.j.a == null)) {
                str = rideTraceHistory.j.a.getName();
            }
            if (!(rideTraceHistory.j == null || rideTraceHistory.j.b == null)) {
                str2 = rideTraceHistory.j.b.getName();
            }
            boolean z = false;
            setmTitleBarTime(rideTraceHistory.k == RideType.RIDE_TYPE, str, str2);
            if (rideTraceHistory.k == RideType.RIDE_TYPE) {
                z = true;
            }
            setmRainBowFlagViewVisible(z);
            this.mShareTime.setText(efv.c(rideTraceHistory.h));
        }
    }

    private void paintStrokeTextView(TextView textView) {
        textView.getPaint().setShadowLayer(5.0f, 0.0f, 0.0f, getResources().getColor(R.color.f_c_1));
    }

    public void setRunTimeView(int i) {
        String a = efv.a((long) i);
        efx.a(this.mRunTime);
        TextView textView = this.mRunTime;
        efx.a();
        efx.a(a, textView);
        this.mRunTime.setText(a);
    }

    public void setRideAverageSpeedView(double d) {
        String b = efv.b(d);
        TextView textView = this.mRunCarlor;
        efx.a();
        efx.a(b, textView);
        efx.a(this.mRunCarlor);
        this.mRunCarlor.setText(b);
    }

    public void setMaxSpeedView(double d) {
        String b = efv.b(d);
        TextView textView = this.mRunSpeed;
        efx.a();
        efx.a(b, textView);
        efx.a(this.mRunSpeed);
        this.mRunSpeed.setText(b);
    }

    public void setDistanceView(int i) {
        String[] a = efv.a(i);
        StringBuilder sb = new StringBuilder();
        sb.append(a[0]);
        sb.append(a[1]);
        String sb2 = sb.toString();
        SpannableString spannableString = new SpannableString(sb2);
        spannableString.setSpan(new AbsoluteSizeSpan(agn.a(AMapPageUtil.getAppContext(), 24.0f)), a[0].length(), sb2.length(), 33);
        this.mRunDistance.setText(spannableString);
        String str = efv.a(i)[0];
        TextView textView = this.mRunDistance;
        efx.a();
        efx.a(str, textView);
    }

    public void setmTitleBarTime(boolean z, String str, String str2) {
        if (z) {
            this.mFootTitleTo.setVisibility(8);
            this.mFootTitleFrom.setVisibility(8);
            return;
        }
        this.mFootTitleTo.setVisibility(0);
        this.mFootTitleFrom.setVisibility(0);
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.mFootTitleFrom.setText(str);
            this.mFootTitleTo.setText(str2);
        }
    }

    public void setmRainBowFlagViewVisible(boolean z) {
        int i = 8;
        this.mRainBowFlagView.setVisibility(z ? 0 : 8);
        View view = this.mLineDivision;
        if (!z) {
            i = 0;
        }
        view.setVisibility(i);
    }

    public void setMapBg(Bitmap bitmap) {
        if (bitmap != null) {
            this.mIvMapBg.setImageBitmap(bitmap);
        }
    }
}
