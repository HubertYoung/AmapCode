package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.view.View;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import java.util.ArrayList;
import java.util.HashMap;

public class BusLineDetailAdapter extends RTBusBaseAdapter<dyh> {
    public static final String BUS_LINE_DETAI_PARAM_FROM_FAVORITE = "bus_line_detai_param_from_favorite";
    public static final String BUS_LINE_EXPEND_INDEX = "bus_line_expend_index";
    public static final String BUS_LINE_NEAR_1KM_STATION_NAME = "bus_line_near_1km_station_name";
    private static final int INVALID_EXPANDED_POSITION_VALUE = -1;
    private static final int NO_INITED_EXPANDED_POSITION_VALUE = -2;
    public static final String RT_BUS_DETAIL_ERROR_TIMES = "rt_bus_detail_error_times";
    public static final String RT_BUS_DETAIL_LOADING = "rt_bus_detail_loading";
    private a m1KmStationThread;
    /* access modifiers changed from: private */
    public Bus mBus;
    /* access modifiers changed from: private */
    public Object mBusLock = new Object();
    private int mExpandedPosition = -2;
    private HashMap<String, RealTimeBusline> mRealTimeBuslines = new HashMap<>();
    /* access modifiers changed from: private */
    public String near1KmStationName = null;
    private boolean rtBusDataInited = false;

    class a extends Thread {
        public a() {
            super("detail_computeNear1KmStationName");
        }

        /* JADX WARNING: Removed duplicated region for block: B:66:0x0129  */
        /* JADX WARNING: Removed duplicated region for block: B:83:0x013a A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r22 = this;
                r1 = r22
            L_0x0002:
                boolean r2 = r22.isInterrupted()
                if (r2 != 0) goto L_0x014d
                r2 = -1
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r3 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this
                java.lang.Object r3 = r3.mBusLock
                monitor-enter(r3)
                r4 = 1148846080(0x447a0000, float:1000.0)
                r5 = 1148862464(0x447a4000, float:1001.0)
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r6 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ all -> 0x0145 }
                com.autonavi.minimap.route.bus.model.Bus r6 = r6.mBus     // Catch:{ all -> 0x0145 }
                if (r6 == 0) goto L_0x004c
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r6 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ all -> 0x0046 }
                com.autonavi.minimap.route.bus.model.Bus r6 = r6.mBus     // Catch:{ all -> 0x0046 }
                int r6 = r6.type     // Catch:{ all -> 0x0046 }
                r7 = 2
                if (r6 == r7) goto L_0x003f
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r6 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ all -> 0x0046 }
                com.autonavi.minimap.route.bus.model.Bus r6 = r6.mBus     // Catch:{ all -> 0x0046 }
                int r6 = r6.type     // Catch:{ all -> 0x0046 }
                r7 = 3
                if (r6 == r7) goto L_0x003f
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r6 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ all -> 0x0046 }
                com.autonavi.minimap.route.bus.model.Bus r6 = r6.mBus     // Catch:{ all -> 0x0046 }
                int r6 = r6.type     // Catch:{ all -> 0x0046 }
                r7 = 10
                if (r6 != r7) goto L_0x004c
            L_0x003f:
                r4 = 1184645120(0x469c4000, float:20000.0)
                r5 = 1184645632(0x469c4200, float:20001.0)
                goto L_0x004c
            L_0x0046:
                r0 = move-exception
                r2 = r0
                r20 = r3
                goto L_0x0149
            L_0x004c:
                com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x011c }
                r9 = 5
                com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition(r9)     // Catch:{ Exception -> 0x011c }
                if (r8 == 0) goto L_0x0115
                com.autonavi.sdk.location.LocationInstrument r8 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x011c }
                com.autonavi.common.model.GeoPoint r8 = r8.getLatestPosition()     // Catch:{ Exception -> 0x011c }
                int r9 = r8.x     // Catch:{ Exception -> 0x011c }
                long r9 = (long) r9     // Catch:{ Exception -> 0x011c }
                int r8 = r8.y     // Catch:{ Exception -> 0x011c }
                long r11 = (long) r8     // Catch:{ Exception -> 0x011c }
                com.autonavi.minimap.map.DPoint r8 = defpackage.cfg.a(r9, r11)     // Catch:{ Exception -> 0x011c }
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r9 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x011c }
                com.autonavi.minimap.route.bus.model.Bus r9 = r9.mBus     // Catch:{ Exception -> 0x011c }
                if (r9 == 0) goto L_0x0115
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r9 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x011c }
                com.autonavi.minimap.route.bus.model.Bus r9 = r9.mBus     // Catch:{ Exception -> 0x011c }
                int[] r9 = r9.stationX     // Catch:{ Exception -> 0x011c }
                if (r9 == 0) goto L_0x0115
                r9 = r5
                r2 = 0
                r5 = -1
            L_0x007e:
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r10 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x0110 }
                com.autonavi.minimap.route.bus.model.Bus r10 = r10.mBus     // Catch:{ Exception -> 0x0110 }
                int[] r10 = r10.stationX     // Catch:{ Exception -> 0x0110 }
                int r10 = r10.length     // Catch:{ Exception -> 0x0110 }
                if (r2 >= r10) goto L_0x00dd
                boolean r10 = r22.isInterrupted()     // Catch:{ Exception -> 0x0110 }
                if (r10 == 0) goto L_0x0091
                monitor-exit(r3)     // Catch:{ all -> 0x0046 }
                return
            L_0x0091:
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r10 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x0110 }
                com.autonavi.minimap.route.bus.model.Bus r10 = r10.mBus     // Catch:{ Exception -> 0x0110 }
                int[] r10 = r10.stationX     // Catch:{ Exception -> 0x0110 }
                r10 = r10[r2]     // Catch:{ Exception -> 0x0110 }
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r11 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x0110 }
                com.autonavi.minimap.route.bus.model.Bus r11 = r11.mBus     // Catch:{ Exception -> 0x0110 }
                int[] r11 = r11.stationY     // Catch:{ Exception -> 0x0110 }
                r11 = r11[r2]     // Catch:{ Exception -> 0x0110 }
                long r12 = (long) r10     // Catch:{ Exception -> 0x0110 }
                long r10 = (long) r11     // Catch:{ Exception -> 0x0110 }
                com.autonavi.minimap.map.DPoint r10 = defpackage.cfg.a(r12, r10)     // Catch:{ Exception -> 0x0110 }
                double r11 = r8.y     // Catch:{ Exception -> 0x0110 }
                double r13 = r8.x     // Catch:{ Exception -> 0x0110 }
                double r6 = r10.y     // Catch:{ Exception -> 0x0110 }
                r21 = r2
                r20 = r3
                double r2 = r10.x     // Catch:{ Exception -> 0x010e }
                r15 = r6
                r17 = r2
                float r2 = defpackage.cfe.a(r11, r13, r15, r17)     // Catch:{ Exception -> 0x010e }
                int r3 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r3 >= 0) goto L_0x00d8
                int r3 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
                if (r3 >= 0) goto L_0x00d8
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r3 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r6 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                com.autonavi.minimap.route.bus.model.Bus r6 = r6.mBus     // Catch:{ Exception -> 0x010e }
                java.lang.String[] r6 = r6.stations     // Catch:{ Exception -> 0x010e }
                r6 = r6[r21]     // Catch:{ Exception -> 0x010e }
                r3.near1KmStationName = r6     // Catch:{ Exception -> 0x010e }
                r9 = r2
                r5 = r21
            L_0x00d8:
                int r2 = r21 + 1
                r3 = r20
                goto L_0x007e
            L_0x00dd:
                r20 = r3
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r2 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                java.lang.String r2 = r2.near1KmStationName     // Catch:{ Exception -> 0x010e }
                boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x010e }
                if (r2 != 0) goto L_0x00fb
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r2 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                java.lang.String r3 = "bus_line_near_1km_station_name"
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r4 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                java.lang.String r4 = r4.near1KmStationName     // Catch:{ Exception -> 0x010e }
                r2.putExtra(r3, r4)     // Catch:{ Exception -> 0x010e }
                r19 = 1
                goto L_0x0126
            L_0x00fb:
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r2 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                java.lang.String r3 = "bus_line_near_1km_station_name"
                boolean r2 = r2.hasExtra(r3)     // Catch:{ Exception -> 0x010e }
                if (r2 == 0) goto L_0x0118
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter r2 = com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.this     // Catch:{ Exception -> 0x010e }
                java.lang.String r3 = "bus_line_near_1km_station_name"
                r2.removeExtra(r3)     // Catch:{ Exception -> 0x010e }
                r6 = 1
                goto L_0x0119
            L_0x010e:
                r0 = move-exception
                goto L_0x0113
            L_0x0110:
                r0 = move-exception
                r20 = r3
            L_0x0113:
                r2 = r0
                goto L_0x0121
            L_0x0115:
                r20 = r3
                r5 = -1
            L_0x0118:
                r6 = 0
            L_0x0119:
                r19 = r6
                goto L_0x0126
            L_0x011c:
                r0 = move-exception
                r20 = r3
                r2 = r0
                r5 = -1
            L_0x0121:
                r2.printStackTrace()     // Catch:{ all -> 0x014b }
                r19 = 0
            L_0x0126:
                monitor-exit(r20)     // Catch:{ all -> 0x014b }
                if (r19 == 0) goto L_0x013a
                r2 = 1
                ebr$a r2 = defpackage.ebr.a(r2)     // Catch:{ InterruptedException -> 0x0137 }
                com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter$a$1 r3 = new com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter$a$1     // Catch:{ InterruptedException -> 0x0137 }
                r3.<init>(r5)     // Catch:{ InterruptedException -> 0x0137 }
                r2.post(r3)     // Catch:{ InterruptedException -> 0x0137 }
                goto L_0x013a
            L_0x0137:
                r0 = move-exception
                r2 = r0
                goto L_0x0141
            L_0x013a:
                r2 = 5000(0x1388, double:2.4703E-320)
                java.lang.Thread.sleep(r2)     // Catch:{ InterruptedException -> 0x0137 }
                goto L_0x0002
            L_0x0141:
                r2.printStackTrace()
                return
            L_0x0145:
                r0 = move-exception
                r20 = r3
            L_0x0148:
                r2 = r0
            L_0x0149:
                monitor-exit(r20)     // Catch:{ all -> 0x014b }
                throw r2
            L_0x014b:
                r0 = move-exception
                goto L_0x0148
            L_0x014d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.adapter.BusLineDetailAdapter.a.run():void");
        }
    }

    public BusLineDetailAdapter(Context context) {
        super(context, R.layout.busline_detail_listview_item);
    }

    public void setIsRTDetailLoading(boolean z) {
        putExtra((String) RT_BUS_DETAIL_LOADING, z);
    }

    public void setRTDetailErrorTimes(int i) {
        putExtra((String) RT_BUS_DETAIL_ERROR_TIMES, i);
    }

    public void setFromFavorite(boolean z) {
        putExtra((String) BUS_LINE_DETAI_PARAM_FROM_FAVORITE, z);
    }

    public int getExpandedPosition() {
        return this.mExpandedPosition;
    }

    public String getNear1KmStationName() {
        return this.near1KmStationName == null ? "" : this.near1KmStationName;
    }

    public HashMap<String, RealTimeBusline> getRealTimeBuslines() {
        return this.mRealTimeBuslines;
    }

    public void setRealTimeBuslines(HashMap<String, RealTimeBusline> hashMap) {
        if (hashMap != null) {
            this.mRealTimeBuslines.clear();
            this.mRealTimeBuslines.putAll(hashMap);
            RealTimeBusline realTimeBusline = this.mRealTimeBuslines.get(this.mBus.id);
            synchronized (this.mBusLock) {
                int count = getCount();
                for (int i = 0; i < count; i++) {
                    dyh dyh = (dyh) getItem(i);
                    if (dyh != null) {
                        dyh.h = realTimeBusline;
                    }
                }
            }
        }
    }

    public void setData(Bus bus) {
        setRTDetailErrorTimes(0);
        synchronized (this.mBusLock) {
            this.mBus = bus;
            ArrayList arrayList = new ArrayList();
            int length = bus.stations != null ? bus.stations.length : 0;
            int length2 = bus.stationIds != null ? bus.stationIds.length : 0;
            int length3 = bus.stationpoiid1 != null ? bus.stationpoiid1.length : 0;
            int length4 = bus.stationstatus != null ? bus.stationstatus.length : 0;
            if (length == length2 && length2 == length3 && length3 == length4) {
                for (int i = 0; i < length; i++) {
                    arrayList.add(new dyh(bus, i));
                }
            }
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

    public void clearData() {
        if (this.m1KmStationThread != null) {
            this.m1KmStationThread.interrupt();
            this.m1KmStationThread = null;
        }
        super.clearData();
    }

    /* access modifiers changed from: private */
    public void initExpandedPosition(int i) {
        if (!this.rtBusDataInited) {
            if (this.mExpandedPosition == -2) {
                this.mExpandedPosition = i;
                putExtra((String) BUS_LINE_EXPEND_INDEX, i);
            }
            if (this.mInteraction instanceof dvk) {
                this.rtBusDataInited = true;
            }
        }
    }

    public boolean onViewClicked(int i, ViewClickAction viewClickAction, dyh dyh, View view) {
        if (viewClickAction == ViewClickAction.BUS_LINE_DETAIL_EXPEND_RT_DATA) {
            if (this.mExpandedPosition == i) {
                this.mExpandedPosition = -1;
                putExtra((String) BUS_LINE_EXPEND_INDEX, this.mExpandedPosition);
            } else {
                this.mExpandedPosition = i;
                putExtra((String) BUS_LINE_EXPEND_INDEX, this.mExpandedPosition);
                notifyDataSetChanged();
            }
            notifyDataSetInvalidated();
            return true;
        } else if (viewClickAction == ViewClickAction.BUS_LINE_DETAIL_ITEM_CLICK || viewClickAction == ViewClickAction.BUS_LINE_DETAIL_REFRESH_RT_DATA) {
            return true;
        } else {
            return false;
        }
    }
}
