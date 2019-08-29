package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.utils.UserDescFeesUtil;
import java.io.File;
import java.text.DecimalFormat;

public class ShareRidingFinshShareView extends LinearLayout {
    private static final String CP_MOBIKE = "mobike";
    private static final String CP_OFO = "ofo";
    private static final int DIS_KM = 1000;
    private static final int TWO = 2;
    private static final int ZERO = 0;
    private ForegroundColorSpan mColorSpan;
    private int mHour = 60;
    private String mHourStr = "h";
    private ImageView mIvCpLogo;
    private ImageView mIvMapBg;
    private View mLineDivision;
    private int mMin = 1;
    private String mMinStr = "min";
    private LinearLayout mRainBowFlagView;
    private TextView mRidingTimeHour;
    private TextView mRidingTimeMin;
    private TextView mRunCarlor;
    private TextView mRunDistance;
    private TextView mRunSpeed;
    private TextView mShareTime;
    private AbsoluteSizeSpan mSizeSpan;
    private ForegroundColorSpan mSmallColorSpan;
    private AbsoluteSizeSpan mSmallSizeSpan;
    private UserDescFeesUtil mUserDescFeesUtil;

    public ShareRidingFinshShareView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.share_bike_finish_share_view, this, true);
        this.mUserDescFeesUtil = new UserDescFeesUtil(this);
        LayoutParams layoutParams = new LayoutParams(ags.a(getContext()).width(), ags.a(getContext()).height());
        this.mIvMapBg = (ImageView) findViewById(R.id.iv_map_bg);
        this.mIvCpLogo = (ImageView) findViewById(R.id.cp_share_logo);
        this.mIvMapBg.setLayoutParams(layoutParams);
        initView();
    }

    private void initView() {
        this.mRunDistance = (TextView) findViewById(R.id.run_distance);
        this.mRidingTimeHour = (TextView) findViewById(R.id.run_time_share);
        this.mRidingTimeMin = (TextView) findViewById(R.id.run_time_share_unit);
        this.mRunCarlor = (TextView) findViewById(R.id.run_carlor);
        this.mRunSpeed = (TextView) findViewById(R.id.run_speed);
        efx.a(this.mRidingTimeHour);
        efx.a(this.mRidingTimeMin);
        efx.a(this.mRunDistance);
        efx.a(this.mRunSpeed);
        efx.a(this.mRunCarlor);
        this.mShareTime = (TextView) findViewById(R.id.run_share_time);
        paintStrokeTextView(this.mShareTime);
        this.mRainBowFlagView = (LinearLayout) findViewById(R.id.rainbow_flag);
        this.mLineDivision = findViewById(R.id.line_division);
    }

    public void bindData(String str, RideTraceHistory rideTraceHistory, OrderDetail orderDetail) {
        setTraceDetail(rideTraceHistory);
        setOrderDetail(str, orderDetail);
        setmRainBowFlagViewVisible(true);
        if (rideTraceHistory != null) {
            updateTime(rideTraceHistory.h);
        }
    }

    private void updateTime(long j) {
        if (this.mShareTime != null && j > 0) {
            this.mShareTime.setText(efv.c(j));
        }
    }

    public void updateTimeStr(String str) {
        if (this.mShareTime != null && !TextUtils.isEmpty(str)) {
            this.mShareTime.setText(str);
        }
    }

    private void paintStrokeTextView(TextView textView) {
        textView.getPaint().setShadowLayer(5.0f, 0.0f, 0.0f, getResources().getColor(R.color.f_c_1));
    }

    private void setUserFeesInfo(OrderDetail orderDetail, boolean z) {
        if (orderDetail != null && orderDetail.isShowMonthCardInfo()) {
            this.mUserDescFeesUtil.setTagDesc(z, orderDetail.tagDesc);
            this.mUserDescFeesUtil.setTagDescVisible(z, 0);
            this.mUserDescFeesUtil.setTagFeesText(orderDetail.fees);
            this.mUserDescFeesUtil.setTagFeesVisible(0);
        }
    }

    private boolean isOfo(String str) {
        return !TextUtils.isEmpty(str) && str.equalsIgnoreCase(CP_OFO);
    }

    public void setOrderDetail(String str, OrderDetail orderDetail) {
        int i;
        int i2 = -1;
        if (orderDetail == null || !orderDetail.result || TextUtils.isEmpty(str)) {
            setRideTime(-1);
            setRideFee(-1);
            return;
        }
        try {
            i = Integer.parseInt(orderDetail.ridingTime);
            try {
                i2 = Integer.parseInt(orderDetail.totalFee);
            } catch (NumberFormatException e) {
                e = e;
                e.printStackTrace();
                setRideTime(i);
                setRideFee(i2);
                setUserFeesInfo(orderDetail, isOfo(str));
            }
        } catch (NumberFormatException e2) {
            e = e2;
            i = -1;
            e.printStackTrace();
            setRideTime(i);
            setRideFee(i2);
            setUserFeesInfo(orderDetail, isOfo(str));
        }
        setRideTime(i);
        setRideFee(i2);
        setUserFeesInfo(orderDetail, isOfo(str));
    }

    public void setDistanceView(int i) {
        if (i < 0) {
            SpannableString spannableString = new SpannableString("- -");
            spannableString.setSpan(getBigSizeSpan(), 0, spannableString.length(), 33);
            this.mRunDistance.setText(spannableString);
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format((double) (((float) i) / 100.0f)));
        sb.append("元");
        String sb2 = sb.toString();
        SpannableString spannableString2 = new SpannableString(sb2);
        setSpan(true, spannableString2, 0, sb2.length() - 1);
        setSpan(false, spannableString2, sb2.length() - 1, sb2.length());
        this.mRunDistance.setText(spannableString2);
    }

    private ForegroundColorSpan getBigColorSpan() {
        if (this.mColorSpan == null) {
            this.mColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.f_c_2));
        }
        return this.mColorSpan;
    }

    private ForegroundColorSpan getSmallColorSpan() {
        if (this.mSmallColorSpan == null) {
            this.mSmallColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.f_c_3));
        }
        return this.mSmallColorSpan;
    }

    private AbsoluteSizeSpan getBigSizeSpan() {
        if (this.mSizeSpan == null) {
            this.mSizeSpan = new AbsoluteSizeSpan(agn.a(getContext(), 26.0f));
        }
        return this.mSizeSpan;
    }

    private AbsoluteSizeSpan getSmallSizeSpan() {
        if (this.mSmallSizeSpan == null) {
            this.mSmallSizeSpan = new AbsoluteSizeSpan(agn.a(getContext(), 13.0f));
        }
        return this.mSmallSizeSpan;
    }

    private void setSpan(boolean z, SpannableString spannableString, int i, int i2) {
        if (spannableString != null && spannableString.length() >= i2) {
            spannableString.setSpan(z ? getBigColorSpan() : getSmallColorSpan(), i, i2, 33);
            spannableString.setSpan(z ? getBigSizeSpan() : getSmallSizeSpan(), i, i2, 33);
        }
    }

    private String[] convertDisToShow(int i) {
        String[] strArr = {"", ""};
        if (i > 1000) {
            strArr[1] = AMapAppGlobal.getApplication().getResources().getString(R.string.running_route_start_running_unit_km);
        } else {
            strArr[1] = AMapAppGlobal.getApplication().getResources().getString(R.string.running_route_start_running_unit_m);
        }
        if (i <= 1000) {
            strArr[0] = String.valueOf(i);
        } else {
            strArr[0] = new DecimalFormat("0.00").format((double) (((float) i) / 1000.0f));
        }
        return strArr;
    }

    private SpannableString getRunHourContent(long j) {
        if (j < ((long) this.mHour)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(j / ((long) this.mHour));
        int length = sb.length();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mHourStr);
        sb2.append(Token.SEPARATOR);
        sb.append(sb2.toString());
        SpannableString spannableString = new SpannableString(sb);
        setSpan(true, spannableString, 0, length);
        setSpan(false, spannableString, length, sb.length());
        return spannableString;
    }

    private SpannableString getRunMinContent(long j) {
        long j2 = j % 60;
        if (j >= ((long) (this.mMin * 2)) && j2 == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(j2);
        int length = sb.length();
        sb.append(this.mMinStr);
        SpannableString spannableString = new SpannableString(sb);
        setSpan(true, spannableString, 0, length);
        setSpan(false, spannableString, length, sb.length());
        return spannableString;
    }

    public void setTraceDetail(RideTraceHistory rideTraceHistory) {
        if (rideTraceHistory != null) {
            setRideDistance((double) rideTraceHistory.c);
            setCarbonReduce((double) ((((float) rideTraceHistory.c) * 189.0f) / 1000.0f));
            return;
        }
        setRideDistance(-1.0d);
        setCarbonReduce(-1.0d);
    }

    private void setRideTime(int i) {
        if (i == -1) {
            this.mRidingTimeHour.setVisibility(0);
            this.mRidingTimeHour.setText("- -");
            this.mRidingTimeMin.setVisibility(8);
            return;
        }
        long j = (long) i;
        SpannableString runHourContent = getRunHourContent(j);
        SpannableString runMinContent = getRunMinContent(j);
        if (runHourContent != null) {
            this.mRidingTimeHour.setVisibility(0);
            this.mRidingTimeHour.setText(runHourContent);
        } else {
            this.mRidingTimeHour.setVisibility(8);
        }
        if (runMinContent != null) {
            this.mRidingTimeMin.setVisibility(0);
            this.mRidingTimeMin.setText(runMinContent);
            return;
        }
        this.mRidingTimeMin.setVisibility(8);
    }

    private void setRideDistance(double d) {
        if (d < 0.0d) {
            this.mRunCarlor.setText("- -");
            return;
        }
        String[] convertDisToShow = convertDisToShow((int) d);
        StringBuilder sb = new StringBuilder();
        sb.append(convertDisToShow[0]);
        sb.append(convertDisToShow[1]);
        String sb2 = sb.toString();
        int length = convertDisToShow[0].length();
        int length2 = sb2.length();
        SpannableString spannableString = new SpannableString(sb2);
        setSpan(true, spannableString, 0, length);
        setSpan(false, spannableString, length, length2);
        this.mRunCarlor.setText(spannableString);
    }

    private void setCarbonReduce(double d) {
        SpannableString spannableString;
        if (d < 0.0d) {
            this.mRunSpeed.setText("- -");
            return;
        }
        if (d > 1000.0d) {
            StringBuilder sb = new StringBuilder();
            sb.append(efv.b(d / 1000.0d));
            sb.append("kg");
            String sb2 = sb.toString();
            spannableString = new SpannableString(sb2);
            setSpan(true, spannableString, 0, sb2.length() - 2);
            setSpan(false, spannableString, sb2.length() - 2, sb2.length());
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append((int) d);
            sb3.append(SuperId.BIT_1_NAVI);
            String sb4 = sb3.toString();
            spannableString = new SpannableString(sb4);
            setSpan(true, spannableString, 0, sb4.length() - 1);
            setSpan(false, spannableString, sb4.length() - 1, sb4.length());
        }
        this.mRunSpeed.setText(spannableString);
    }

    private void setRideFee(int i) {
        if (i < 0) {
            SpannableString spannableString = new SpannableString("- -");
            spannableString.setSpan(getBigSizeSpan(), 0, spannableString.length(), 33);
            this.mRunDistance.setText(spannableString);
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();
        sb.append(decimalFormat.format((double) (((float) i) / 100.0f)));
        sb.append("元");
        String sb2 = sb.toString();
        SpannableString spannableString2 = new SpannableString(sb2);
        spannableString2.setSpan(new AbsoluteSizeSpan(agn.a(getContext(), 39.0f)), 0, sb2.length() - 1, 33);
        spannableString2.setSpan(getSmallSizeSpan(), sb2.length() - 1, sb2.length(), 33);
        this.mRunDistance.setText(spannableString2);
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

    public void setCpLogo(Bitmap bitmap) {
        if (bitmap == null) {
            this.mIvCpLogo.setVisibility(8);
            return;
        }
        this.mIvCpLogo.setImageBitmap(bitmap);
        this.mIvCpLogo.requestLayout();
    }

    public void setCpLogoUrl(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            setDefaultCpSrc(str2);
            return;
        }
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            setDefaultCpSrc(str2);
            return;
        }
        this.mIvCpLogo.setImageURI(Uri.fromFile(file));
        this.mIvCpLogo.requestLayout();
    }

    private void setDefaultCpSrc(String str) {
        if (CP_MOBIKE.equalsIgnoreCase(str)) {
            this.mIvCpLogo.setImageResource(R.drawable.share_bike_logo_mobile);
        } else if (CP_OFO.equalsIgnoreCase(str)) {
            this.mIvCpLogo.setImageResource(R.drawable.share_bike_logo_ofo);
        } else {
            this.mIvCpLogo.setImageResource(R.drawable.share_bike_cp_default_logo);
        }
    }

    public ImageView getCpLogoView() {
        this.mIvCpLogo.setVisibility(0);
        return this.mIvCpLogo;
    }
}
