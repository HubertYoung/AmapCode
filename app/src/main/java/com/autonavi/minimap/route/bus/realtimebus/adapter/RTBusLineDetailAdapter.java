package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RTBusLineDetailAdapter extends RTBusBaseAdapter<dyh> {
    public static final String BUS_LINE_SEL_ITEM_INDEX = "bus_line_sel_item_index";
    public static final String RT_BUS_LINE_NEAR_1KM_STATION_NAME = "rt_bus_line_near_1km_station_name";
    private a m1KmStationThread;
    private String mAdcode = "";
    /* access modifiers changed from: private */
    public Bus mBus;
    /* access modifiers changed from: private */
    public Object mBusLock = new Object();
    private int mCurrentSelIndex = -1;
    private HashMap<String, RealTimeBusAndStationMatchup> mMatchupsMap;

    class a extends Thread {
        public a() {
            super("rt_detail_computeNear1KmStationName");
        }

        public final void run() {
            boolean z;
            boolean z2;
            while (!isInterrupted()) {
                synchronized (RTBusLineDetailAdapter.this.mBusLock) {
                    float f = 1000.0f;
                    float f2 = 1001.0f;
                    try {
                        if (RTBusLineDetailAdapter.this.mBus != null && (RTBusLineDetailAdapter.this.mBus.type == 2 || RTBusLineDetailAdapter.this.mBus.type == 3 || RTBusLineDetailAdapter.this.mBus.type == 10)) {
                            f = 20000.0f;
                            f2 = 20001.0f;
                        }
                        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                            DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
                            if (!(RTBusLineDetailAdapter.this.mBus == null || RTBusLineDetailAdapter.this.mBus.stationX == null)) {
                                String str = null;
                                float f3 = f2;
                                int i = 0;
                                while (i < RTBusLineDetailAdapter.this.mBus.stationX.length) {
                                    if (!isInterrupted()) {
                                        DPoint a3 = cfg.a((long) RTBusLineDetailAdapter.this.mBus.stationX[i], (long) RTBusLineDetailAdapter.this.mBus.stationY[i]);
                                        DPoint dPoint = a2;
                                        e = str;
                                        float a4 = cfe.a(a2.y, a2.x, a3.y, a3.x);
                                        if (a4 < f && a4 < f3) {
                                            f3 = a4;
                                            str = RTBusLineDetailAdapter.this.mBus.stations[i];
                                        }
                                        i++;
                                        a2 = dPoint;
                                    } else {
                                        return;
                                    }
                                }
                                if (!TextUtils.isEmpty(str)) {
                                    RTBusLineDetailAdapter.this.putExtra((String) RTBusLineDetailAdapter.RT_BUS_LINE_NEAR_1KM_STATION_NAME, str);
                                    z = true;
                                } else if (RTBusLineDetailAdapter.this.hasExtra(RTBusLineDetailAdapter.RT_BUS_LINE_NEAR_1KM_STATION_NAME)) {
                                    RTBusLineDetailAdapter.this.removeExtra(RTBusLineDetailAdapter.RT_BUS_LINE_NEAR_1KM_STATION_NAME);
                                    z2 = true;
                                }
                            }
                        }
                        z2 = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        z = false;
                        if (z) {
                            try {
                                ebr.a(true).post(new Runnable() {
                                    public final void run() {
                                        RTBusLineDetailAdapter.this.notifyDataSetChanged();
                                    }
                                });
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                        Thread.sleep(5000);
                    } finally {
                        while (true) {
                            boolean z3 = e;
                        }
                    }
                }
            }
        }
    }

    public RTBusLineDetailAdapter(Context context) {
        super(context, R.layout.route_realtime_busline_detail_listview_item);
        loadAttentionRecord();
    }

    private void loadAttentionRecord() {
        this.mMatchupsMap = new HashMap<>();
        List loadAll = bso.a().a.loadAll();
        if (loadAll != null && loadAll.size() > 0) {
            this.mMatchupsMap = dxu.a(loadAll);
        }
    }

    public void setData(Bus bus) {
        synchronized (this.mBusLock) {
            this.mBus = bus;
            this.mCurrentSelIndex = -1;
            ArrayList arrayList = new ArrayList();
            int length = bus.stations != null ? bus.stations.length : 0;
            int length2 = bus.stationIds != null ? bus.stationIds.length : 0;
            int length3 = bus.stationpoiid1 != null ? bus.stationpoiid1.length : 0;
            int length4 = bus.stationstatus != null ? bus.stationstatus.length : 0;
            if (length == length2 && length2 == length3 && length3 == length4) {
                for (int i = 0; i < length; i++) {
                    dyh dyh = new dyh(bus, i);
                    dyh.g = isAttention(dyh.d);
                    arrayList.add(dyh);
                }
            }
            putExtra((String) BUS_LINE_SEL_ITEM_INDEX, this.mCurrentSelIndex);
            setListData(arrayList);
        }
        startFind1KmStation();
    }

    private void startFind1KmStation() {
        if (this.m1KmStationThread != null) {
            this.m1KmStationThread.interrupt();
            this.m1KmStationThread = null;
        }
        this.m1KmStationThread = new a();
        this.m1KmStationThread.start();
    }

    public void setAdcode(String str) {
        this.mAdcode = str;
    }

    public void clearData() {
        this.mCurrentSelIndex = -1;
        if (this.mMatchupsMap != null) {
            this.mMatchupsMap.clear();
        }
        if (this.m1KmStationThread != null) {
            this.m1KmStationThread.interrupt();
            this.m1KmStationThread = null;
        }
        super.clearData();
    }

    public boolean onViewClicked(int i, ViewClickAction viewClickAction, dyh dyh, View view) {
        if (viewClickAction == ViewClickAction.BUS_LINE_DETAIL_ITEM_CHECK) {
            this.mCurrentSelIndex = dyh.b;
            putExtra((String) BUS_LINE_SEL_ITEM_INDEX, this.mCurrentSelIndex);
            if (this.mInteraction instanceof dvn) {
                ((dvn) this.mInteraction).a(true);
            }
            notifyDataSetChanged();
        } else if (viewClickAction == ViewClickAction.BUS_LINE_DETAIL_ITEM_UNCHECK && this.mCurrentSelIndex == dyh.b) {
            this.mCurrentSelIndex = -1;
            putExtra((String) BUS_LINE_SEL_ITEM_INDEX, this.mCurrentSelIndex);
            if (this.mInteraction instanceof dvn) {
                ((dvn) this.mInteraction).a(false);
            }
            notifyDataSetChanged();
        }
        return false;
    }

    private boolean isAttention(String str) {
        return this.mMatchupsMap != null && this.mMatchupsMap.containsKey(str);
    }

    public btd getWannaAttentionStation() {
        btd btd;
        dyh dyh = (dyh) getItem(this.mCurrentSelIndex);
        if (dyh == null) {
            return null;
        }
        synchronized (this.mBusLock) {
            Bus bus = this.mBus;
            int i = dyh.b;
            btd = new btd();
            btd.adcode = this.mAdcode;
            btd.station_id = dyh.d;
            btd.station_name = dyh.c;
            GeoPoint geoPoint = new GeoPoint(bus.stationX[i], bus.stationY[i]);
            btd.station_lat = Double.valueOf(geoPoint.getLatitude());
            btd.station_lon = Double.valueOf(geoPoint.getLongitude());
            btd.bus_areacode = bus.areacode;
            btd.poiid1 = dyh.e;
            btd.bus_id = bus.id;
            String str = bus.name;
            int indexOf = str.indexOf("(");
            if (indexOf >= 0) {
                str = str.substring(0, indexOf);
            }
            btd.bus_name = str;
            btd.bus_describe = bus.endName;
        }
        return btd;
    }
}
