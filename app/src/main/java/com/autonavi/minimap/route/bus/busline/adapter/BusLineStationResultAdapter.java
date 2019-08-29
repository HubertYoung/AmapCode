package com.autonavi.minimap.route.bus.busline.adapter;

import android.content.Context;
import android.view.View;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusBaseAdapter;
import com.autonavi.minimap.route.bus.realtimebus.adapter.ViewClickAction;

public class BusLineStationResultAdapter extends RTBusBaseAdapter<POI> {
    private IBusLineResult mBusLineData;

    public BusLineStationResultAdapter(Context context) {
        super(context, R.layout.busline_search_result_item_layout);
    }

    public void setData(IBusLineResult iBusLineResult) {
        this.mBusLineData = iBusLineResult;
        if (iBusLineResult != null) {
            setListData(iBusLineResult.getPoiList(1));
        }
    }

    public boolean onViewClicked(int i, ViewClickAction viewClickAction, POI poi, View view) {
        if (this.mInteraction instanceof dvl) {
            if (viewClickAction == ViewClickAction.BUS_LINE_STATION_ITEM_CLICK) {
                ((dvl) this.mInteraction).a(i, poi, this.mBusLineData);
            } else if (viewClickAction == ViewClickAction.BUS_LINE_STATION_ROUTE_CLICK) {
                ((dvl) this.mInteraction).b(i, poi, this.mBusLineData);
            } else if (viewClickAction == ViewClickAction.BUS_LINE_STATION_CALL_CLICK) {
                ((dvl) this.mInteraction).a(poi);
            }
        }
        return false;
    }
}
