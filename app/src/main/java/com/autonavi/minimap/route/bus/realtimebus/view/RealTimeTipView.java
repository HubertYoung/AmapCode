package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusFollowSettingPage;
import java.util.Iterator;

@Deprecated
public class RealTimeTipView extends FrameLayout {
    private static agk<RealTimeTipView> sObjectContainer = new agk<>();
    private ImageView ivCollect;
    /* access modifiers changed from: private */
    public RealTimeBusAndStationMatchup mMatchup;
    /* access modifiers changed from: private */
    public bso mRealBusSearchHelper;
    /* access modifiers changed from: private */
    public bid page;
    private TextView tvAlert;
    private TextView tvAttention;
    private TextView tvLineName;
    private TextView tvLineTo;
    private TextView tvStation;

    public static void notifyRealTimeDataChanged(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        if (realTimeBusAndStationMatchup != null && sObjectContainer != null && sObjectContainer.a() != 0) {
            Iterator it = sObjectContainer.iterator();
            while (it.hasNext()) {
                RealTimeTipView realTimeTipView = (RealTimeTipView) it.next();
                if (!(realTimeTipView == null || realTimeTipView.mMatchup == null || !RealTimeBusAndStationMatchup.isBusStationMatch(realTimeTipView.mMatchup, realTimeBusAndStationMatchup))) {
                    realTimeTipView.onDataRefresh(realTimeBusAndStationMatchup);
                }
            }
        }
    }

    public RealTimeTipView(bid bid) {
        super(bid.getContext());
        this.page = bid;
        sObjectContainer.a(this);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        if (layoutInflater != null) {
            layoutInflater.inflate(R.layout.busline_home_tips, this);
        }
        getContext();
        this.mRealBusSearchHelper = bso.a();
        initView();
    }

    public RealTimeTipView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        sObjectContainer.a(this);
    }

    private void initView() {
        this.tvAttention = (TextView) findViewById(R.id.tv_attention);
        this.tvStation = (TextView) findViewById(R.id.tv_station);
        this.tvAlert = (TextView) findViewById(R.id.tv_alert_time);
        this.tvLineName = (TextView) findViewById(R.id.tv_line_name);
        this.tvLineTo = (TextView) findViewById(R.id.tv_lineto);
        this.ivCollect = (ImageView) findViewById(R.id.iv_collect);
        this.tvAttention.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (RealTimeTipView.this.mMatchup != null) {
                    if (!RealTimeTipView.this.mRealBusSearchHelper.a(RealTimeTipView.this.mMatchup.mBuslineID, RealTimeTipView.this.mMatchup.mStationID) && RealTimeTipView.this.mRealBusSearchHelper.b() >= 50) {
                        ToastHelper.showToast(RealTimeTipView.this.getResources().getString(R.string.real_time_bus_attention_upperlimit));
                    } else if (RealTimeTipView.this.page != null) {
                        PageBundle pageBundle = new PageBundle();
                        if (RealTimeTipView.this.mMatchup != null) {
                            pageBundle.putObject("RealTimeBusSettingFragmnet.IBusLineResult", RealTimeTipView.this.mMatchup);
                        }
                        pageBundle.putInt("bundle_key_pageId", 0);
                        RealTimeTipView.this.page.startPage(RTBusFollowSettingPage.class, pageBundle);
                    }
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
    }

    public void bindData(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        int i;
        this.mMatchup = realTimeBusAndStationMatchup;
        if (this.mMatchup != null) {
            if (realTimeBusAndStationMatchup.mTrip != null) {
                ebj.b(this.tvStation, ebj.a(getContext(), 1, realTimeBusAndStationMatchup.mTrip.arrival, realTimeBusAndStationMatchup.mTrip.station_left), realTimeBusAndStationMatchup.stationName());
            }
            this.tvLineName.setText(realTimeBusAndStationMatchup.mBuslineName);
            if (this.mRealBusSearchHelper.a(realTimeBusAndStationMatchup.mBuslineID, realTimeBusAndStationMatchup.mStationID)) {
                this.ivCollect.setVisibility(0);
                this.tvAlert.setVisibility(0);
                this.tvAttention.setText(getResources().getString(R.string.busline_setting_edit));
                btd b = this.mRealBusSearchHelper.b(realTimeBusAndStationMatchup.mBuslineID, realTimeBusAndStationMatchup.mStationID);
                if (b != null) {
                    if (!TextUtils.isEmpty(b.is_push)) {
                        String str = b.alert_time;
                        try {
                            i = ahh.b(str.split(":")[0]);
                        } catch (NumberFormatException unused) {
                            i = 0;
                        }
                        String str2 = str.split(":")[1];
                        if (i >= 13) {
                            TextView textView = this.tvAlert;
                            StringBuilder sb = new StringBuilder();
                            sb.append(getResources().getString(R.string.busline_alert_time));
                            sb.append(":");
                            sb.append(getResources().getString(R.string.date_pm));
                            sb.append(Token.SEPARATOR);
                            sb.append(i - 12);
                            sb.append(":");
                            sb.append(str2);
                            textView.setText(sb.toString());
                        } else if (i == 0) {
                            TextView textView2 = this.tvAlert;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(getResources().getString(R.string.busline_alert_time));
                            sb2.append(":");
                            sb2.append(getResources().getString(R.string.date_pm));
                            sb2.append(" 12:");
                            sb2.append(str2);
                            textView2.setText(sb2.toString());
                        } else {
                            TextView textView3 = this.tvAlert;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(getResources().getString(R.string.busline_alert_time));
                            sb3.append(":");
                            sb3.append(getResources().getString(R.string.date_am));
                            sb3.append(Token.SEPARATOR);
                            sb3.append(str);
                            textView3.setText(sb3.toString());
                        }
                    } else {
                        this.tvAlert.setVisibility(8);
                    }
                } else {
                    return;
                }
            } else {
                this.tvAlert.setVisibility(8);
                this.ivCollect.setVisibility(8);
                this.tvAttention.setText(getResources().getString(R.string.busline_setting_attention));
            }
            if (this.tvLineTo != null) {
                if (TextUtils.isEmpty(realTimeBusAndStationMatchup.startName()) || TextUtils.isEmpty(realTimeBusAndStationMatchup.endName())) {
                    setFromToStation(realTimeBusAndStationMatchup, this.tvLineTo);
                } else {
                    TextView textView4 = this.tvLineTo;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(realTimeBusAndStationMatchup.startName());
                    sb4.append(" - ");
                    sb4.append(realTimeBusAndStationMatchup.endName());
                    textView4.setText(sb4.toString());
                }
            }
        }
    }

    private void onDataRefresh(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        if (getVisibility() == 0) {
            bindData(realTimeBusAndStationMatchup);
        }
    }

    private void setFromToStation(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, final TextView textView) {
        BusLineSearch.a(realTimeBusAndStationMatchup.mBuslineID, realTimeBusAndStationMatchup.adcode(), (Callback<IBusLineSearchResult>) new RealTimeBusSearchCallback() {
            public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
                Bus busLine = iBusLineSearchResult.getBusLine(0);
                if (busLine != null && busLine.startName != null && busLine.endName != null) {
                    TextView textView = textView;
                    StringBuilder sb = new StringBuilder();
                    sb.append(busLine.startName);
                    sb.append(" - ");
                    sb.append(busLine.endName);
                    textView.setText(sb.toString());
                }
            }
        }, true);
    }
}
