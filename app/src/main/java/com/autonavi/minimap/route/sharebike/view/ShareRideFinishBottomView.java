package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.utils.UserDescFeesUtil;
import java.text.DecimalFormat;

public class ShareRideFinishBottomView extends LinearLayout {
    private ForegroundColorSpan mColorSpan;
    private int mHour;
    private String mHourStr;
    private int mMin;
    private String mMinStr;
    private TextView mRidingTimeHour;
    private TextView mRidingTimeMin;
    private TextView mRunCarlor;
    private TextView mRunDistance;
    private TextView mRunSpeed;
    private AbsoluteSizeSpan mSizeSpan;
    private ForegroundColorSpan mSmallColorSpan;
    private AbsoluteSizeSpan mSmallSizeSpan;
    private UserDescFeesUtil mUserDescFeesUtil;

    public ShareRideFinishBottomView(Context context) {
        this(context, null);
    }

    public ShareRideFinishBottomView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShareRideFinishBottomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMin = 1;
        this.mHour = 60;
        this.mMinStr = "min";
        this.mHourStr = "h";
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.share_ride_finish_bottom_detail, this, true);
        this.mUserDescFeesUtil = new UserDescFeesUtil(this);
        this.mRunDistance = (TextView) findViewById(R.id.run_distance);
        this.mRidingTimeHour = (TextView) findViewById(R.id.run_time);
        this.mRidingTimeMin = (TextView) findViewById(R.id.run_time_unit);
        this.mRunCarlor = (TextView) findViewById(R.id.run_carlor);
        this.mRunSpeed = (TextView) findViewById(R.id.run_speed);
        efx.a(this.mRidingTimeHour);
        efx.a(this.mRidingTimeMin);
        efx.a(this.mRunDistance);
        efx.a(this.mRunSpeed);
        efx.a(this.mRunCarlor);
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
        return !TextUtils.isEmpty(str) && str.equalsIgnoreCase("ofo");
    }

    public void setOrderDetail(String str, OrderDetail orderDetail) {
        if (orderDetail == null || !orderDetail.result || TextUtils.isEmpty(str)) {
            setRideTime(-1);
            setRideFee(-1);
            return;
        }
        int parseInt = Integer.parseInt(orderDetail.ridingTime);
        int parseInt2 = Integer.parseInt(orderDetail.totalFee);
        setRideTime(parseInt);
        setRideFee(parseInt2);
        setUserFeesInfo(orderDetail, isOfo(str));
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

    @NonNull
    private SpannableString getRunTimeContent(long j) {
        int i;
        int i2;
        int i3;
        if (j < ((long) (this.mMin * 2))) {
            StringBuilder sb = new StringBuilder();
            sb.append("1");
            int length = sb.length();
            sb.append(this.mMinStr);
            SpannableString spannableString = new SpannableString(sb);
            setSpan(true, spannableString, 0, length);
            setSpan(true, spannableString, length, sb.length());
            return spannableString;
        } else if (j % ((long) this.mHour) != 0 || j < ((long) this.mHour)) {
            StringBuilder sb2 = new StringBuilder();
            long j2 = j / ((long) this.mHour);
            long j3 = (j / ((long) this.mMin)) % 60;
            int i4 = -1;
            if (j2 > 0) {
                sb2.append(j2);
                i2 = sb2.length();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.mHourStr);
                sb3.append(Token.SEPARATOR);
                sb2.append(sb3.toString());
                i = sb2.length();
            } else {
                i2 = -1;
                i = -1;
            }
            if (j3 > 0) {
                sb2.append(j3);
                i4 = sb2.length();
                sb2.append(this.mMinStr);
                i3 = sb2.length();
            } else {
                i3 = -1;
            }
            SpannableString spannableString2 = new SpannableString(sb2);
            if (i2 > 0 && i > 0) {
                setSpan(true, spannableString2, 0, i2);
                setSpan(false, spannableString2, i2, i);
            }
            if (i4 <= 0 || i3 <= 0) {
                return spannableString2;
            }
            setSpan(true, spannableString2, i + 1, i4 - 1);
            setSpan(false, spannableString2, i4, spannableString2.length());
            return spannableString2;
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(j / ((long) this.mHour));
            int length2 = sb4.length();
            sb4.append(this.mHourStr);
            SpannableString spannableString3 = new SpannableString(sb4);
            setSpan(true, spannableString3, 0, length2);
            setSpan(false, spannableString3, length2, sb4.length());
            return spannableString3;
        }
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
}
