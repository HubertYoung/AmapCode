package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;

public class RTBusAttentionListItemView extends LinearLayout implements dxy<RealTimeBusAndStationMatchup> {
    ImageView alertIconView;
    public TextView alertInfoView;
    private View alertView;
    public TextView countView;
    public TextView directionView;
    private View infoView;
    ImageView ivProgress;
    public TextView labelView;
    protected dxz mClickListener;
    protected RealTimeBusAndStationMatchup mData;
    protected int mPositionId;
    private View realTimeView;
    public TextView stationView;
    public TextView timeView;
    ImageView warningView;

    public boolean isDataChanged(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        return true;
    }

    public RTBusAttentionListItemView(Context context) {
        super(context);
    }

    public RTBusAttentionListItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RTBusAttentionListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.labelView = (TextView) findViewById(R.id.tv_item_realtime_bus_label);
        this.stationView = (TextView) findViewById(R.id.tv_item_realtime_bus_station);
        this.directionView = (TextView) findViewById(R.id.tv_item_realtime_bus_direction);
        this.countView = (TextView) findViewById(R.id.tv_item_realtime_bus_num);
        this.timeView = (TextView) findViewById(R.id.tv_item_realtime_bus_time);
        this.alertInfoView = (TextView) findViewById(R.id.realtimebus_bus_alert);
        this.infoView = findViewById(R.id.layout_item_follow_busstop);
        this.alertView = findViewById(R.id.layout_item_follow_busstop_alert);
        this.realTimeView = findViewById(R.id.layout_item_realtime_bus);
        this.warningView = (ImageView) findViewById(R.id.iv_warning_realtime);
        this.alertIconView = (ImageView) findViewById(R.id.realtime_bus_alert_icon);
        this.ivProgress = (ImageView) findViewById(R.id.iv_progress);
    }

    public void bindData(int i, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, Bundle bundle) {
        if (realTimeBusAndStationMatchup != null) {
            this.mPositionId = i;
            this.mData = realTimeBusAndStationMatchup;
            setView(realTimeBusAndStationMatchup);
            NoDBClickUtil.a(this.infoView, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (RTBusAttentionListItemView.this.mClickListener != null) {
                        RTBusAttentionListItemView.this.mClickListener.onViewClicked(RTBusAttentionListItemView.this.mPositionId, ViewClickAction.RT_BUS_VIEW_ITEM_CLICK, RTBusAttentionListItemView.this.mData, view);
                    }
                }
            });
            NoDBClickUtil.a(this.alertView, (OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (RTBusAttentionListItemView.this.mClickListener != null) {
                        RTBusAttentionListItemView.this.mClickListener.onViewClicked(RTBusAttentionListItemView.this.mPositionId, ViewClickAction.RT_BUS_ATTENTION_VIEW_SETTING, RTBusAttentionListItemView.this.mData, view);
                    }
                }
            });
        }
    }

    public RealTimeBusAndStationMatchup getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
    }

    private void setView(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        String str;
        Context context = getContext();
        this.labelView.setText(realTimeBusAndStationMatchup.mBean.bus_name);
        TextView textView = this.stationView;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.arriving_in_station));
        sb.append(realTimeBusAndStationMatchup.mBean.station_name);
        textView.setText(sb.toString());
        TextView textView2 = this.directionView;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(realTimeBusAndStationMatchup.mBean.bus_describe);
        sb2.append(context.getString(R.string.direction));
        textView2.setText(sb2.toString());
        if (realTimeBusAndStationMatchup.isLoadFinish()) {
            dxx.a(this.ivProgress);
            this.countView.setVisibility(0);
            if (realTimeBusAndStationMatchup.retryTimes < 2) {
                this.realTimeView.setVisibility(0);
                this.warningView.setVisibility(8);
                switch (realTimeBusAndStationMatchup.mStatus) {
                    case 0:
                        this.countView.setText(context.getString(R.string.real_time_bus_no_data));
                        this.timeView.setVisibility(8);
                        break;
                    case 1:
                        String a = ebj.a(context, 5, realTimeBusAndStationMatchup.mTrip.arrival, realTimeBusAndStationMatchup.mTrip.station_left);
                        this.countView.setText(a);
                        if (!a.equals(context.getString(R.string.arrived_in_station)) && !a.equals(context.getString(R.string.about_to_arriving_in_station))) {
                            this.timeView.setVisibility(0);
                            TextView textView3 = this.timeView;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(context.getString(R.string.autonavi_search_result_about));
                            sb3.append(axt.a(realTimeBusAndStationMatchup.mTrip.arrival, true));
                            textView3.setText(sb3.toString());
                            break;
                        } else {
                            this.timeView.setVisibility(8);
                            break;
                        }
                    case 2:
                        this.countView.setText(context.getString(R.string.real_time_bus_no_bus));
                        this.timeView.setVisibility(8);
                        break;
                    case 3:
                        this.countView.setText(context.getString(R.string.real_time_bus_no_time));
                        this.timeView.setVisibility(8);
                        break;
                }
            } else {
                this.warningView.setVisibility(0);
                this.realTimeView.setVisibility(8);
            }
        } else {
            this.warningView.setVisibility(8);
            this.realTimeView.setVisibility(0);
            this.timeView.setVisibility(8);
            this.countView.setVisibility(8);
            dxx.a(getContext(), this.ivProgress);
        }
        if (TextUtils.isEmpty(realTimeBusAndStationMatchup.mBean.is_push)) {
            this.alertInfoView.setText(context.getString(R.string.busline_attention_not_set));
            this.alertIconView.setImageResource(R.drawable.icon_rt_follow_warning);
            return;
        }
        this.alertIconView.setImageResource(R.drawable.icon_rt_follow_warning_hd);
        String str2 = realTimeBusAndStationMatchup.mBean.alert_time;
        int b = ahh.b(str2.split(":")[0]);
        String str3 = str2.split(":")[1];
        if (b > 12) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(context.getString(R.string.date_pm));
            sb4.append(b - 12);
            sb4.append(":");
            sb4.append(str3);
            sb4.append(Token.SEPARATOR);
            sb4.append(context.getString(R.string.busline_alert));
            str = sb4.toString();
        } else if (b == 0) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(context.getString(R.string.date_pm));
            sb5.append("12:");
            sb5.append(str3);
            sb5.append(Token.SEPARATOR);
            sb5.append(context.getString(R.string.busline_alert));
            str = sb5.toString();
        } else {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(context.getString(R.string.date_am));
            sb6.append(str2);
            sb6.append(Token.SEPARATOR);
            sb6.append(context.getString(R.string.busline_alert));
            str = sb6.toString();
        }
        setAlertText(this.alertInfoView, str);
    }

    private void setAlertText(TextView textView, String str) {
        if (str.length() >= 2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.f_c_2)), 2, str.length() - 2, 34);
            textView.setText(spannableStringBuilder);
        }
    }
}
