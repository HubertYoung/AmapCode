package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;

public class RTBusAroundListItemBaseView extends RelativeLayout implements dxy<RealTimeBusAndStationMatchup> {
    private TextView bus_number;
    private TextView drive_to_station_name;
    protected dxz mClickListener;
    protected RealTimeBusAndStationMatchup mData;
    protected int mPositionId;

    public boolean isDataChanged(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        return true;
    }

    public RTBusAroundListItemBaseView(Context context) {
        this(context, null);
    }

    public RTBusAroundListItemBaseView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RTBusAroundListItemBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.bus_number = (TextView) findViewById(R.id.bus_number);
        this.drive_to_station_name = (TextView) findViewById(R.id.drive_to_station_name);
    }

    private void setBusBaseInfo(boolean z, String str, String str2) {
        if (z) {
            this.bus_number.setBackgroundResource(R.drawable.rt_list_bus_number_bg);
            this.bus_number.setTextColor(getContext().getResources().getColor(R.color.rt_list_bus_number_tx_color));
        } else {
            this.bus_number.setBackgroundResource(R.drawable.rt_list_bus_number_bg_light);
            this.bus_number.setTextColor(getContext().getResources().getColor(R.color.f_c_16));
        }
        this.bus_number.setText(str);
        TextView textView = this.drive_to_station_name;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(getContext().getString(R.string.direction));
        textView.setText(sb.toString());
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
        NoDBClickUtil.a((View) this, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (RTBusAroundListItemBaseView.this.mClickListener != null) {
                    RTBusAroundListItemBaseView.this.mClickListener.onViewClicked(RTBusAroundListItemBaseView.this.mPositionId, ViewClickAction.RT_BUS_VIEW_ITEM_CLICK, RTBusAroundListItemBaseView.this.mData, view);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public boolean isRTBusData() {
        return (this.mData == null || this.mData.mBean == null || !this.mData.mBean.isRealTimeBus()) ? false : true;
    }

    public void bindData(int i, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, Bundle bundle) {
        if (realTimeBusAndStationMatchup != null) {
            this.mPositionId = i;
            this.mData = realTimeBusAndStationMatchup;
            setBusBaseInfo(isRTBusData(), this.mData.busName(), this.mData.busDescribe());
        }
    }

    public RealTimeBusAndStationMatchup getData() {
        return this.mData;
    }
}
