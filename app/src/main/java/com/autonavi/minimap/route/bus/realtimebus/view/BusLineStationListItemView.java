package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;

public class BusLineStationListItemView extends RelativeLayout implements dxy<POI> {
    private TextView addressView;
    private Button callButton;
    private View callView;
    /* access modifiers changed from: private */
    public dxz mClickListener;
    /* access modifiers changed from: private */
    public POI mData;
    /* access modifiers changed from: private */
    public int mPositionId;
    private OnClickListener mViewClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (BusLineStationListItemView.this.mClickListener != null) {
                if (view.getId() == R.id.poi_item_layout_rl) {
                    BusLineStationListItemView.this.mClickListener.onViewClicked(BusLineStationListItemView.this.mPositionId, ViewClickAction.BUS_LINE_STATION_ITEM_CLICK, BusLineStationListItemView.this.mData, view);
                } else if (view.getId() == R.id.btn_to_map_rl) {
                    BusLineStationListItemView.this.mClickListener.onViewClicked(BusLineStationListItemView.this.mPositionId, ViewClickAction.BUS_LINE_STATION_ROUTE_CLICK, BusLineStationListItemView.this.mData, view);
                } else {
                    if (view.getId() == R.id.btn_to_call_rl) {
                        BusLineStationListItemView.this.mClickListener.onViewClicked(BusLineStationListItemView.this.mPositionId, ViewClickAction.BUS_LINE_STATION_CALL_CLICK, BusLineStationListItemView.this.mData, view);
                    }
                }
            }
        }
    };
    private TextView nameView;
    private View rootView;
    private View routeView;

    public boolean isDataChanged(POI poi) {
        return true;
    }

    public BusLineStationListItemView(Context context) {
        super(context);
    }

    public BusLineStationListItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BusLineStationListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.addressView = (TextView) findViewById(R.id.poiAddr);
        this.nameView = (TextView) findViewById(R.id.poiName);
        this.callView = findViewById(R.id.btn_to_call_rl);
        this.routeView = findViewById(R.id.btn_to_map_rl);
        this.rootView = findViewById(R.id.poi_item_layout_rl);
        this.callButton = (Button) findViewById(R.id.btn_to_call);
    }

    public void bindData(int i, POI poi, Bundle bundle) {
        this.mPositionId = i;
        this.mData = poi;
        TextView textView = this.nameView;
        StringBuilder sb = new StringBuilder();
        sb.append(i + 1);
        sb.append(".");
        sb.append(poi.getName());
        textView.setText(sb.toString());
        this.addressView.setText(poi.getAddr());
        NoDBClickUtil.a(this.rootView, this.mViewClickListener);
        NoDBClickUtil.a(this.routeView, this.mViewClickListener);
        NoDBClickUtil.a(this.callView, this.mViewClickListener);
        if (TextUtils.isEmpty(poi.getPhone())) {
            this.callView.setEnabled(false);
            this.callButton.setEnabled(false);
            return;
        }
        this.callView.setEnabled(true);
        this.callButton.setEnabled(true);
    }

    public POI getData() {
        return this.mData;
    }

    public void setOnViewClickListener(dxz dxz) {
        this.mClickListener = dxz;
    }
}
