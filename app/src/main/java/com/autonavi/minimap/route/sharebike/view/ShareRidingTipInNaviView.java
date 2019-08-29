package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.utils.UserDescFeesUtil;
import java.text.DecimalFormat;

public class ShareRidingTipInNaviView extends RelativeLayout {
    private boolean mHasInitRidingTickTime;
    private boolean mIsFreshRideState;
    private boolean mIsVip;
    private TextView mRidingDetail;
    private TextView mRidingNotice;
    private TextView mRidingTime;
    private TipType mTipType;
    private TextView mUnlockDes;
    private TextView mUnlockRemindTime;
    private UserDescFeesUtil mUserFeesCpUtils;

    public enum TipType {
        UNLOCKING,
        RIDING
    }

    public ShareRidingTipInNaviView(Context context) {
        this(context, null);
    }

    public ShareRidingTipInNaviView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShareRidingTipInNaviView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        setBackgroundResource(R.color.whole_bg);
        LayoutInflater.from(getContext()).inflate(R.layout.share_bike_tip_in_navi_layout, this, true);
        this.mUserFeesCpUtils = new UserDescFeesUtil(this);
        this.mRidingTime = (TextView) findViewById(R.id.share_bike_ride_time);
        this.mRidingDetail = (TextView) findViewById(R.id.share_bike_using_detail_tv);
        this.mRidingNotice = (TextView) findViewById(R.id.share_bike_using_tip_tv);
        this.mUnlockRemindTime = (TextView) findViewById(R.id.share_bike_unlock_tip_tv);
        this.mUnlockDes = (TextView) findViewById(R.id.share_bike_unlock_des_tv);
        efx.a(this.mRidingTime);
    }

    public void setType(TipType tipType) {
        if (tipType != null) {
            this.mTipType = tipType;
            if (TipType.UNLOCKING.equals(this.mTipType)) {
                setUnlockViewVisible(0);
            } else if (TipType.RIDING.equals(this.mTipType)) {
                setUnlockViewVisible(8);
            }
            setRidingDetailVisible(8);
        }
    }

    public void setCountDownTime(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i <= 0 || !this.mIsFreshRideState || this.mIsVip) {
            if (i <= 0) {
                setType(TipType.RIDING);
            }
            return;
        }
        setType(TipType.UNLOCKING);
        StringBuilder sb = new StringBuilder();
        sb.append(j);
        sb.append("s");
        this.mUnlockRemindTime.setText(sb.toString());
    }

    public void setRidingDetail(RideState rideState, boolean z) {
        if (rideState == null) {
            setRidingDetailVisible(8);
            hideUserFeesView();
            return;
        }
        this.mIsFreshRideState = true;
        boolean isVip = rideState.isVip();
        this.mIsVip = isVip;
        if (isVip) {
            setUserFeesInfo(rideState);
            setRidingDetailVisible(8);
        } else if (this.mTipType == null || !TipType.UNLOCKING.equals(this.mTipType)) {
            if (!z) {
                CharSequence convertContent = convertContent(rideState);
                if (!TextUtils.isEmpty(convertContent)) {
                    setRidingDetailVisible(0);
                    this.mRidingDetail.setText(convertContent);
                } else {
                    setRidingDetailVisible(8);
                }
            }
            hideUserFeesView();
        } else {
            return;
        }
        if (!z) {
            updateInitTickTime(rideState);
            updateRidingtime(rideState);
        }
    }

    public void releaseTickTimer() {
        egm.a().b();
    }

    private void updateInitTickTime(RideState rideState) {
        if (rideState != null && rideState.result && !this.mHasInitRidingTickTime) {
            egm.a().b();
            egm.a().a(rideState.durationSec);
            egm.a().a((b) new b() {
                public final void a(final long j) {
                    ebr.a(true).post(new Runnable() {
                        public final void run() {
                            ShareRidingTipInNaviView.this.updateRidingTime(j);
                        }
                    });
                }
            });
            this.mHasInitRidingTickTime = true;
        }
    }

    private void setUserFeesInfo(RideState rideState) {
        if (rideState != null && this.mUserFeesCpUtils != null) {
            this.mUserFeesCpUtils.setTagFeesText(rideState.fees);
            this.mUserFeesCpUtils.setTagFeesVisible(0);
            this.mUserFeesCpUtils.setTagDesc(rideState.isOfo(), rideState.tag_desc);
            this.mUserFeesCpUtils.setTagDescVisible(rideState.isOfo(), 0);
        }
    }

    private void hideUserFeesView() {
        if (this.mUserFeesCpUtils != null) {
            this.mUserFeesCpUtils.setTagFeesVisible(8);
            this.mUserFeesCpUtils.setTagDescGone();
        }
    }

    private void setUnlockViewVisible(int i) {
        this.mUnlockRemindTime.setVisibility(i);
        this.mUnlockDes.setVisibility(i);
    }

    private void setRidingDetailVisible(int i) {
        this.mRidingDetail.setVisibility(i);
        this.mRidingNotice.setVisibility(i);
        this.mRidingTime.setVisibility(i);
    }

    private CharSequence convertContent(RideState rideState) {
        StringBuilder sb = new StringBuilder();
        if (rideState == null || !rideState.result) {
            return sb;
        }
        float f = rideState.cost / 100.0f;
        if (f < 0.0f) {
            return sb;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        StringBuilder sb2 = new StringBuilder("  ");
        sb2.append(decimalFormat.format((double) f));
        sb2.append("元");
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb);
        sb4.append(sb3);
        return sb4.toString();
    }

    private void updateRidingtime(RideState rideState) {
        if (rideState == null || !rideState.result) {
            updateRidingTime(-1);
            if (this.mRidingTime != null) {
                this.mRidingTime.setVisibility(8);
            }
        } else if (this.mRidingTime != null) {
            this.mRidingTime.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void updateRidingTime(long j) {
        String str = "";
        if (j >= 0) {
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String format = decimalFormat.format(j % 60);
            String format2 = decimalFormat.format((j / 60) % 60);
            String format3 = decimalFormat.format(j / 3600);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder(" (");
            sb2.append(format3);
            sb2.append(":");
            sb2.append(format2);
            sb2.append(":");
            sb2.append(format);
            sb2.append(")");
            sb.append(sb2.toString());
            str = sb.toString();
        }
        if (this.mRidingTime != null) {
            this.mRidingTime.setText(str);
        }
    }
}
