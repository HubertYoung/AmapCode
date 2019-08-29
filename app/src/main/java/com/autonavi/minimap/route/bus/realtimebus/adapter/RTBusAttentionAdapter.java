package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import org.json.JSONObject;

public class RTBusAttentionAdapter extends RTBusBaseAdapter<RealTimeBusAndStationMatchup> {
    private dvp mInteraction;

    public RTBusAttentionAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public dxy getItemView(int i, ViewGroup viewGroup) {
        return (dxy) this.mLayoutInflater.inflate(R.layout.route_realtime_item_bus_line, viewGroup, false);
    }

    public void setRTBusListItemInteraction(dvp dvp) {
        this.mInteraction = dvp;
    }

    public boolean onViewClicked(int i, ViewClickAction viewClickAction, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, View view) {
        switch (viewClickAction) {
            case RT_BUS_VIEW_ITEM_CLICK:
                if (this.mInteraction != null) {
                    this.mInteraction.a(realTimeBusAndStationMatchup.busId(), realTimeBusAndStationMatchup.busAreacode());
                    break;
                }
                break;
            case RT_BUS_ATTENTION_VIEW_SETTING:
                dys.a((String) "P00264", (String) "B001", (JSONObject) null);
                if (this.mInteraction != null) {
                    this.mInteraction.c(realTimeBusAndStationMatchup);
                    break;
                }
                break;
            default:
                return false;
        }
        return true;
    }
}
