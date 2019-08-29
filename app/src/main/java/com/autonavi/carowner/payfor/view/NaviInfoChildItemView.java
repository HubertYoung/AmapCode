package com.autonavi.carowner.payfor.view;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;

public class NaviInfoChildItemView extends RelativeLayout {
    private final Button applyPayfor = ((Button) findViewById(R.id.apply_payfor));
    private final View certificationed = findViewById(R.id.apply_certificationed);
    private final TextView from = ((TextView) findViewById(R.id.from_address));
    /* access modifiers changed from: private */
    public a listener;
    private final TextView naviInfoTV = ((TextView) findViewById(R.id.navi_info));
    private final TextView stateInfo = ((TextView) findViewById(R.id.state_info));
    private final View timeLine = findViewById(R.id.time_line);
    private final TextView to = ((TextView) findViewById(R.id.to_address));

    public interface a {
        void onApplyPayfor(PayforNaviData payforNaviData);
    }

    public NaviInfoChildItemView(Context context) {
        super(context);
        setBackgroundColor(-1);
        inflate(context, R.layout.activities_naviinfo_item, this);
    }

    private String getRestTime(int i) {
        int i2 = i / 60;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 / 60);
            sb.append(getContext().getString(R.string.error_hour));
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 <= 0) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(i3);
            sb3.append(getContext().getString(R.string.minutes));
            return sb3.toString();
        } else if (i2 == 0) {
            return getContext().getString(R.string.error_less_one_minues);
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i2);
            sb4.append(getContext().getString(R.string.error_minutes));
            return sb4.toString();
        }
    }

    private String formarDistance(int i) {
        if (i >= 1000) {
            StringBuilder sb = new StringBuilder();
            sb.append(((float) Math.round((((float) i) / 1000.0f) * 10.0f)) / 10.0f);
            sb.append(getContext().getString(R.string.error_km));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i);
        sb2.append(getContext().getString(R.string.error_meter));
        return sb2.toString();
    }

    public void setData(final PayforNaviData payforNaviData, boolean z) {
        this.from.setText(payforNaviData.fromAddress);
        this.to.setText(payforNaviData.toAddress);
        this.naviInfoTV.setText(getContext().getString(R.string.activities_navi_infos, new Object[]{formarDistance(payforNaviData.distance), getRestTime(payforNaviData.timeUsed), Integer.valueOf(payforNaviData.averageSpeed)}));
        if (payforNaviData.haveReported) {
            this.stateInfo.setVisibility(0);
            this.applyPayfor.setVisibility(4);
            if (payforNaviData.checkState == 0) {
                this.stateInfo.setText(R.string.activities_navi_reported_unchecked);
                this.certificationed.setVisibility(4);
            } else if (payforNaviData.checkState == 1) {
                if (PayforNaviData.isNeedShowMoney(payforNaviData.payedMoney)) {
                    this.stateInfo.setText(Html.fromHtml(getContext().getString(R.string.activities_navi_report_payed, new Object[]{Double.valueOf(payforNaviData.payedMoney)})));
                } else {
                    this.stateInfo.setText(R.string.activities_navi_report_payed_without_count);
                }
                this.certificationed.setVisibility(0);
            } else if (payforNaviData.checkState == 2) {
                this.stateInfo.setText(R.string.activities_navi_reported_check_fail);
                this.certificationed.setVisibility(4);
            }
        } else {
            this.stateInfo.setVisibility(4);
            this.certificationed.setVisibility(4);
            this.applyPayfor.setVisibility(0);
            if (z) {
                this.applyPayfor.setEnabled(false);
                return;
            }
            this.applyPayfor.setTag(payforNaviData);
            this.applyPayfor.setEnabled(true);
            NoDBClickUtil.a((View) this.applyPayfor, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (NaviInfoChildItemView.this.listener != null) {
                        NaviInfoChildItemView.this.listener.onApplyPayfor(payforNaviData);
                    }
                }
            });
        }
    }

    public void setOnApplyBtnClickListener(a aVar) {
        this.listener = aVar;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        measureTimeLine(0, 1);
    }

    /* access modifiers changed from: private */
    public void measureTimeLine(final int i, final int i2) {
        int height = getHeight();
        if (height > 0) {
            this.timeLine.setMinimumHeight(height);
        } else if (i <= 10) {
            postDelayed(new Runnable() {
                public final void run() {
                    NaviInfoChildItemView.this.measureTimeLine(i + 1, i2 + 1);
                }
            }, (long) i2);
        }
    }
}
