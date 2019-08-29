package com.autonavi.minimap.route.run.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;

public class RouteRunShareView extends LinearLayout {
    private TextView mFootTitleFrom;
    private TextView mFootTitleTo;
    private ImageView mIvMapBg = ((ImageView) findViewById(R.id.iv_map_bg));
    private TextView mKcalText;
    private LinearLayout mRainBowFlagView;
    private TextView mRunCarlor;
    private TextView mRunDistance;
    private ImageView mRunPageIcon;
    private TextView mRunSpeed;
    private TextView mRunSpeedUnit;
    private TextView mRunTime;
    private TextView mRunTitle;

    public RouteRunShareView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.route_run_finish_bottom, this, true);
        LayoutParams layoutParams = new LayoutParams(ags.a(getContext()).width(), ags.a(getContext()).height());
        this.mIvMapBg.setLayoutParams(layoutParams);
        initView();
    }

    private void initView() {
        this.mRunDistance = (TextView) findViewById(R.id.run_distance);
        this.mKcalText = (TextView) findViewById(R.id.kcal_text);
        this.mRunTime = (TextView) findViewById(R.id.run_time_share);
        this.mRunSpeed = (TextView) findViewById(R.id.run_speed);
        this.mRunCarlor = (TextView) findViewById(R.id.run_carlor);
        this.mRunSpeedUnit = (TextView) findViewById(R.id.run_speed_unit);
        this.mRainBowFlagView = (LinearLayout) findViewById(R.id.rainbow_flag);
        this.mRunPageIcon = (ImageView) findViewById(R.id.run_finish_page_icon);
        this.mRunTitle = (TextView) findViewById(R.id.route_title_run);
        paintStrokeTextView(this.mRunTitle);
        this.mFootTitleFrom = (TextView) findViewById(R.id.route_title_foot_from);
        this.mFootTitleTo = (TextView) findViewById(R.id.route_title_foot_to);
        paintStrokeTextView(this.mFootTitleFrom);
        paintStrokeTextView(this.mFootTitleTo);
    }

    public void bindData(RunTraceHistory runTraceHistory, String str) {
        efx.a(this.mRunTime);
        efx.a(this.mRunDistance);
        efx.a(this.mRunSpeed);
        efx.a(this.mRunCarlor);
        setDistanceView(runTraceHistory.c);
        setRunTimeView(runTraceHistory.b);
        boolean z = false;
        setRunSpeedView(runTraceHistory.b, runTraceHistory.c, runTraceHistory.j == RunType.RUN_TYPE);
        setRunCarlorView(runTraceHistory.d);
        boolean z2 = runTraceHistory.j == RunType.RUN_TYPE;
        if (!z2 && TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapPageUtil.getAppContext().getString(R.string.foot_end_kcal_text_head));
            sb.append(ecm.a(runTraceHistory.d));
            str = sb.toString();
        }
        setFootRunDiffUI(z2, str);
        String str2 = "";
        String str3 = "";
        if (!(runTraceHistory.i == null || runTraceHistory.i.a == null)) {
            str2 = runTraceHistory.i.a.getName();
        }
        if (!(runTraceHistory.i == null || runTraceHistory.i.b == null)) {
            str3 = runTraceHistory.i.b.getName();
        }
        String b = efv.b(runTraceHistory.g);
        if (runTraceHistory.j == RunType.RUN_TYPE) {
            z = true;
        }
        setmTitleBarTime(b, z, str2, str3);
        this.mRunTitle.setText(efv.c(runTraceHistory.g));
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

    public void setRunSpeedView(int i, int i2, boolean z) {
        if (z) {
            String a = efv.a((double) (((float) i) / (((float) i2) / 1000.0f)));
            TextView textView = this.mRunSpeed;
            efx.a();
            efx.a(a, textView);
            efx.a(this.mRunSpeed);
            this.mRunSpeed.setText(a);
            this.mRunSpeedUnit.setText(AMapPageUtil.getAppContext().getString(R.string.foot_end_speed_unit));
            return;
        }
        this.mRunSpeed.setText(String.valueOf((int) (((double) i2) / (((double) i) / 60.0d))));
        this.mRunSpeedUnit.setText(AMapPageUtil.getAppContext().getString(R.string.foot_end_average_speed_unit));
    }

    public void setRunTimeView(int i) {
        String a = efv.a((long) i);
        efx.a(this.mRunTime);
        TextView textView = this.mRunTime;
        efx.a();
        efx.a(a, textView);
        this.mRunTime.setText(a);
    }

    public void setRunCarlorView(int i) {
        String valueOf = String.valueOf(i);
        TextView textView = this.mRunCarlor;
        efx.a();
        efx.a(valueOf, textView);
        this.mRunCarlor.setText(String.valueOf(i));
    }

    public void setFootRunDiffUI(boolean z, String str) {
        this.mRainBowFlagView.setVisibility(z ? 0 : 8);
        this.mKcalText.setVisibility(z ? 8 : 0);
        this.mRunPageIcon.setImageResource(z ? R.drawable.run_finish_page_icon : R.drawable.route_foot_end_icon);
        if (z || TextUtils.isEmpty(str)) {
            this.mKcalText.setVisibility(8);
            return;
        }
        this.mKcalText.setVisibility(0);
        this.mKcalText.setText(str);
    }

    public void setmTitleBarTime(String str, boolean z, String str2, String str3) {
        this.mRunTitle.setText(str);
        if (z) {
            this.mFootTitleTo.setVisibility(8);
            this.mFootTitleFrom.setVisibility(8);
            return;
        }
        this.mFootTitleTo.setVisibility(0);
        this.mFootTitleFrom.setVisibility(0);
        this.mFootTitleFrom.setText(str2);
        this.mFootTitleTo.setText(str3);
    }

    private void paintStrokeTextView(TextView textView) {
        textView.getPaint().setShadowLayer(5.0f, 0.0f, 0.0f, getResources().getColor(R.color.f_c_1));
    }

    public void setMapBg(Bitmap bitmap) {
        if (bitmap != null) {
            this.mIvMapBg.setImageBitmap(bitmap);
        }
    }
}
