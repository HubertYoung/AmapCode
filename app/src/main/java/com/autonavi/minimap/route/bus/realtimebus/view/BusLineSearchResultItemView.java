package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import java.util.Locale;

public class BusLineSearchResultItemView extends LinearLayout implements dxy<Bus> {
    protected dxz mClickListener;
    /* access modifiers changed from: private */
    public Bus mData;
    /* access modifiers changed from: private */
    public int mPositionId;
    private TextView price;
    private TextView status;
    private TextView textDes;
    private TextView textView;
    private TextView timeEnd;
    private TextView timeStart;

    public boolean isDataChanged(Bus bus) {
        return true;
    }

    public BusLineSearchResultItemView(Context context) {
        super(context);
    }

    public BusLineSearchResultItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BusLineSearchResultItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.textView = (TextView) findViewById(R.id.main_des);
        this.textDes = (TextView) findViewById(R.id.sub_des);
        this.status = (TextView) findViewById(R.id.busline_result_status_desc);
        this.price = (TextView) findViewById(R.id.price);
        this.timeStart = (TextView) findViewById(R.id.timeStart);
        this.timeEnd = (TextView) findViewById(R.id.timeEnd);
    }

    public void bindData(int i, Bus bus, Bundle bundle) {
        this.mPositionId = i;
        this.mData = bus;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mData.startName);
        sb.append(" - ");
        sb.append(this.mData.endName);
        String sb2 = sb.toString();
        this.textView.setText(this.mData.key_name);
        this.textDes.setText(sb2);
        if (this.mData.startTime >= 0) {
            int i2 = this.mData.startTime;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(i2 / 100);
            sb3.append(":");
            sb3.append(getMinute(i2 % 100));
            this.timeStart.setText(Token.SEPARATOR.concat(String.valueOf(sb3.toString())));
            this.timeStart.setVisibility(0);
        } else {
            this.timeStart.setVisibility(8);
        }
        if (this.mData.endTime >= 0) {
            int i3 = this.mData.endTime;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i3 / 100);
            sb4.append(":");
            sb4.append(getMinute(i3 % 100));
            this.timeEnd.setText(Token.SEPARATOR.concat(String.valueOf(sb4.toString())));
            this.timeEnd.setVisibility(0);
        } else {
            this.timeEnd.setVisibility(8);
        }
        String ticketDesc = this.mData.getTicketDesc();
        if (TextUtils.isEmpty(ticketDesc)) {
            this.price.setVisibility(8);
        } else {
            this.price.setVisibility(0);
            this.price.setText(ticketDesc);
        }
        this.status.setVisibility(8);
        if (this.mData.isRealTime) {
            if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                this.status.setBackgroundResource(R.drawable.busline_result_status_bkg_nine);
            }
            this.status.setVisibility(0);
            this.status.setText(getContext().getResources().getString(R.string.real_time_bus));
        }
        if (this.mData.status != 1) {
            if (Build.BRAND.toLowerCase(Locale.getDefault()).contains("xiaomi") || VERSION.SDK_INT <= 16) {
                this.status.setBackgroundResource(R.drawable.busline_result_status_bkg_nine);
            }
            this.status.setVisibility(0);
            if (this.mData.status == 0) {
                this.status.setText(getContext().getResources().getString(R.string.busline_result_status_outage));
            } else if (this.mData.status == 2) {
                this.status.setText(getContext().getResources().getString(R.string.busline_result_status_planning));
            } else if (this.mData.status == 3) {
                this.status.setText(getContext().getResources().getString(R.string.busline_result_status_under_construction));
            }
        }
    }

    public Bus getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
        NoDBClickUtil.a((View) this, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (BusLineSearchResultItemView.this.mClickListener != null) {
                    BusLineSearchResultItemView.this.mClickListener.onViewClicked(BusLineSearchResultItemView.this.mPositionId, ViewClickAction.BUS_LINE_SEARCH_RESULT_ITEM, BusLineSearchResultItemView.this.mData, view);
                }
            }
        });
    }

    private String getMinute(int i) {
        String valueOf = String.valueOf(i);
        return i < 10 ? "0".concat(String.valueOf(valueOf)) : valueOf;
    }
}
