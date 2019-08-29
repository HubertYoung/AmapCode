package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RTBusAroundAdapter extends RTBusBaseAdapter<RealTimeBusAndStationMatchup> {
    public static final String EXT_RT_BUS_RETRY_TIMES = "rt_bus_retry_times";
    private int mCurrentStationIndex = -1;
    private boolean mIsSupportRTBus = false;
    private boolean mNextForceSort = false;
    private List<dyi> mStations;
    private boolean mWatingRTBusData = true;

    public RTBusAroundAdapter(Context context) {
        super(context);
    }

    public boolean setIsSupportRTBus(boolean z) {
        boolean z2 = this.mIsSupportRTBus != z;
        this.mIsSupportRTBus = z;
        return z2;
    }

    public void setData(List<dyi> list, boolean z) {
        if (list != null) {
            this.mStations = list;
            if (z) {
                this.mCurrentStationIndex = -1;
            } else if (this.mWatingRTBusData) {
                this.mNextForceSort = true;
                this.mWatingRTBusData = z;
            }
            this.mNextForceSort = false;
            this.mWatingRTBusData = z;
        }
    }

    public void clearData() {
        this.mStations = null;
        super.clearData();
    }

    public int getCurrentStationIndex() {
        return this.mCurrentStationIndex;
    }

    public dyi showStation(int i) {
        if (this.mStations == null || i < 0 || i >= this.mStations.size()) {
            super.clearData();
            return null;
        }
        boolean z = this.mCurrentStationIndex != i;
        this.mCurrentStationIndex = i;
        dyi dyi = this.mStations.get(i);
        ArrayList<RealTimeBusAndStationMatchup> arrayList = dyi.f;
        if (z || this.mNextForceSort) {
            this.mNextForceSort = false;
            Collections.sort(arrayList, new a());
        }
        setListData(arrayList);
        return dyi;
    }

    /* access modifiers changed from: protected */
    public dxy getItemView(int i, ViewGroup viewGroup) {
        return (dxy) this.mLayoutInflater.inflate(this.mIsSupportRTBus ? R.layout.route_rtbus_around_item_layout : R.layout.route_rtbus_around_item_base_layout, viewGroup, false);
    }

    public boolean onViewClicked(int i, ViewClickAction viewClickAction, RealTimeBusAndStationMatchup realTimeBusAndStationMatchup, View view) {
        if (this.mInteraction instanceof dvo) {
            dvo dvo = (dvo) this.mInteraction;
            switch (viewClickAction) {
                case RT_BUS_VIEW_STAR:
                    dvo.b(realTimeBusAndStationMatchup);
                    break;
                case RT_BUS_VIEW_UNSTAR:
                    dvo.a(realTimeBusAndStationMatchup);
                    break;
                case RT_BUS_VIEW_ITEM_CLICK:
                    dvo.a(realTimeBusAndStationMatchup.busId(), realTimeBusAndStationMatchup.busAreacode());
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
}
