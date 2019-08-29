package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.utils.UserDescFeesUtil;
import java.text.DecimalFormat;

public class ShareRidingDisplayTipView extends RelativeLayout {
    private boolean hasInitTick;
    private TextView mElectronicTip;
    private TextView mErrorReport;
    private TextView mFinishRiding;
    /* access modifiers changed from: private */
    public a mListener;
    /* access modifiers changed from: private */
    public boolean mNeedShow = true;
    private b mOnTickListener;
    private TextView mRidingDistance;
    private TextView mRidingDistanceUnit;
    private TextView mRidingFee;
    private TextView mRidingFeeUnit;
    /* access modifiers changed from: private */
    public TextView mRidingTime;
    private ViewType mType;
    private UserDescFeesUtil mUserFeesCpUtils;

    public enum ViewType {
        DEFAULT,
        MECHANICAL,
        ELECTRONIC
    }

    public interface a {
        void a();

        void b();

        void c();
    }

    public ShareRidingDisplayTipView(Context context) {
        super(context);
        initView(ViewType.DEFAULT);
    }

    public ShareRidingDisplayTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(ViewType.DEFAULT);
    }

    public ShareRidingDisplayTipView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(ViewType.DEFAULT);
    }

    public void setOnFeeTipClickListener(a aVar) {
        this.mListener = aVar;
    }

    private void initView(ViewType viewType) {
        LayoutInflater.from(getContext()).inflate(R.layout.share_riding_display_tip_view, this, true);
        setViewType(ViewType.MECHANICAL);
        setData(null, false);
        setUserFeesInfo(null);
    }

    private void initElectronicView() {
        initCommonView();
        findViewById(R.id.share_riding_electronic_lock).setVisibility(0);
        findViewById(R.id.share_riding_mechanical_lock).setVisibility(8);
        this.mUserFeesCpUtils = new UserDescFeesUtil(this);
    }

    private void initMachinicalView() {
        initCommonView();
        findViewById(R.id.share_riding_electronic_lock).setVisibility(8);
        findViewById(R.id.share_riding_mechanical_lock).setVisibility(0);
        this.mUserFeesCpUtils = new UserDescFeesUtil(this);
        initOnclickListener();
        initTickThread();
    }

    private void initCommonView() {
        this.mRidingFee = (TextView) findViewById(R.id.share_riding_fee_count);
        this.mRidingFeeUnit = (TextView) findViewById(R.id.share_riding_fee_unit);
        this.mRidingDistance = (TextView) findViewById(R.id.share_riding_distance_count);
        this.mRidingDistanceUnit = (TextView) findViewById(R.id.share_riding_distance_unit);
        this.mRidingTime = (TextView) findViewById(R.id.share_riding_time);
        efx.a(this.mRidingFee);
        efx.a(this.mRidingTime);
        efx.a(this.mRidingDistance);
    }

    private void initTickThread() {
        this.mOnTickListener = new b() {
            public final void a(long j) {
                if (ShareRidingDisplayTipView.this.mNeedShow) {
                    ShareRidingDisplayTipView.this.setTimeDisplay(j);
                }
            }
        };
        egm.a().a(this.mOnTickListener);
    }

    /* access modifiers changed from: private */
    public void setTimeDisplay(final long j) {
        if (this.mRidingTime != null) {
            this.mRidingTime.post(new Runnable() {
                public final void run() {
                    ShareRidingDisplayTipView.this.mRidingTime.setText(ShareRidingDisplayTipView.this.getRidingTime(j));
                }
            });
        }
    }

    public void releaseTimer() {
        egm.a().a((b) null);
        egm.a().b();
    }

    private void setUserFeesInfo(RideState rideState) {
        if (rideState == null || !rideState.isVip()) {
            this.mUserFeesCpUtils.setTagDescGone();
            return;
        }
        this.mUserFeesCpUtils.setTagDesc(rideState.isOfo(), rideState.tag_desc);
        this.mUserFeesCpUtils.setTagDescVisible(rideState.isOfo(), 0);
        this.mUserFeesCpUtils.setTagFeesText(rideState.fees);
        this.mUserFeesCpUtils.setTagFeesTextColor(getResources().getColor(R.color.f_c_2));
        this.mUserFeesCpUtils.setTagFeesVisible(0);
    }

    private void initOnclickListener() {
        this.mFinishRiding = (TextView) findViewById(R.id.share_riding_mechanical_finish_riding_bt);
        if (this.mFinishRiding != null) {
            this.mFinishRiding.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (ShareRidingDisplayTipView.this.mListener != null) {
                        ShareRidingDisplayTipView.this.mListener.a();
                    }
                }
            });
        }
        this.mElectronicTip = (TextView) findViewById(R.id.share_riding_electronic_notice_tip);
        if (this.mElectronicTip != null) {
            this.mElectronicTip.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (ShareRidingDisplayTipView.this.mListener != null) {
                        ShareRidingDisplayTipView.this.mListener.b();
                    }
                }
            });
        }
        this.mErrorReport = (TextView) findViewById(R.id.share_riding_mechanical_error_report);
        if (this.mErrorReport != null) {
            this.mErrorReport.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (ShareRidingDisplayTipView.this.mListener != null) {
                        ShareRidingDisplayTipView.this.mListener.c();
                    }
                }
            });
        }
    }

    private void setViewType(ViewType viewType) {
        if (this.mType != viewType) {
            switch (viewType) {
                case DEFAULT:
                case ELECTRONIC:
                    initElectronicView();
                    break;
                case MECHANICAL:
                    initMachinicalView();
                    break;
            }
            this.mType = viewType;
            postInvalidate();
            requestLayout();
        }
    }

    public void updateElectronicTipText(String str) {
        if (str != null && !TextUtils.isEmpty(str) && this.mElectronicTip != null) {
            this.mElectronicTip.setText(str);
        }
    }

    public void resetTimer() {
        this.hasInitTick = false;
        setTimeDisplay(-1);
        egm.a().a(this.mOnTickListener);
    }

    public void setData(RideState rideState, boolean z) {
        String str;
        if (rideState == null || !rideState.result || rideState.status != 1) {
            this.mNeedShow = false;
            setTimeDisplay(-1);
            this.mRidingFee.setText("- -");
            return;
        }
        updateViewType(rideState);
        this.mNeedShow = true;
        if (!this.hasInitTick && z) {
            egm.a().a(rideState.durationSec);
            this.hasInitTick = true;
        }
        if (rideState.isVip()) {
            this.mRidingFeeUnit.setVisibility(8);
            this.mRidingFee.setVisibility(8);
        } else {
            this.mRidingFeeUnit.setVisibility(0);
            this.mRidingFee.setVisibility(0);
        }
        setUserFeesInfo(rideState);
        requestLayout();
        float f = rideState.cost / 100.0f;
        if (f >= 0.0f) {
            if (Float.compare(rideState.cost % 100.0f, 0.0f) == 0) {
                str = new DecimalFormat("0").format((double) f);
            } else {
                str = new DecimalFormat("0.0").format((double) f);
            }
            this.mRidingFee.setText(str);
        }
    }

    /* access modifiers changed from: private */
    public CharSequence getRidingTime(long j) {
        String str = "- -";
        String str2 = "- -";
        String str3 = "- -";
        DecimalFormat decimalFormat = new DecimalFormat("00");
        if (j >= 0) {
            str3 = decimalFormat.format(j % 60);
            str2 = decimalFormat.format((j / 60) % 60);
            str = decimalFormat.format(j / 3600);
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        sb2.append(":");
        sb2.append(str3);
        sb.append(sb2.toString());
        return sb.toString();
    }

    public void updateDistanceView(int i) {
        String[] convertDisToShow = convertDisToShow(i);
        if (this.mRidingDistance != null) {
            this.mRidingDistance.setText(convertDisToShow[0]);
        }
        if (this.mRidingDistanceUnit != null) {
            this.mRidingDistanceUnit.setText(convertDisToShow[1]);
        }
    }

    private String[] convertDisToShow(int i) {
        String[] strArr = {"", ""};
        if (i > 1000) {
            strArr[1] = "公里";
        } else {
            strArr[1] = "米";
        }
        if (i <= 1000) {
            strArr[0] = String.valueOf(i);
        } else {
            strArr[0] = new DecimalFormat(DiskFormatter.FORMAT).format((double) (((float) i) / 1000.0f));
        }
        return strArr;
    }

    public ViewType getLockType() {
        return this.mType;
    }

    public void updateViewType(RideState rideState) {
        if (rideState != null && rideState.result) {
            if (rideState.lockType == 0) {
                if (this.mType != ViewType.MECHANICAL) {
                    setViewType(ViewType.MECHANICAL);
                    this.mType = ViewType.MECHANICAL;
                }
            } else if (rideState.lockType == 1) {
                if (this.mType != ViewType.ELECTRONIC) {
                    setViewType(ViewType.ELECTRONIC);
                    this.mType = ViewType.ELECTRONIC;
                }
            } else if (rideState.lockType == -1 && this.mType != ViewType.DEFAULT) {
                setViewType(ViewType.DEFAULT);
                this.mType = ViewType.DEFAULT;
            }
        }
    }
}
