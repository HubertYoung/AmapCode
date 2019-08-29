package defpackage;

import android.os.Handler;
import android.os.Message;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import java.util.ArrayList;
import java.util.HashSet;

/* renamed from: ayh reason: default package */
/* compiled from: BusCommuteRealTimeRefreshManager */
public final class ayh {
    private a a;

    /* renamed from: ayh$a */
    /* compiled from: BusCommuteRealTimeRefreshManager */
    public static class a extends Handler {
        /* access modifiers changed from: private */
        public static int a = 30000;
        /* access modifiers changed from: private */
        public static int b = 10000;
        /* access modifiers changed from: private */
        public int c;
        private Runnable d;
        /* access modifiers changed from: private */
        public boolean e;

        /* synthetic */ a(Runnable runnable, byte b2) {
            this(runnable);
        }

        private a(Runnable runnable) {
            this.c = a;
            this.e = true;
            this.d = runnable;
        }

        public final void handleMessage(Message message) {
            azb.a(null, "deng--- refresh real time bus");
            if (this.d != null) {
                this.d.run();
            }
            if (this.e) {
                sendEmptyMessageDelayed(1, (long) this.c);
            }
        }
    }

    public ayh(Runnable runnable) {
        this.a = new a(runnable, 0);
    }

    private void a(int i) {
        if (this.a != null) {
            this.a.c = i;
        }
    }

    public final void a(BusPath busPath, BusRealTimeResponse busRealTimeResponse) {
        if (busPath != null && busPath.segmentlist != null && busPath.segmentlist.size() != 0 && busRealTimeResponse != null && busRealTimeResponse.buses != null && busRealTimeResponse.buses.size() != 0) {
            HashSet hashSet = new HashSet();
            for (int i = 0; i < busPath.segmentlist.size(); i++) {
                BusSegment busSegment = busPath.segmentlist.get(i);
                if (busSegment != null) {
                    hashSet.add(busSegment.startid);
                }
            }
            if (hashSet.size() != 0) {
                for (int i2 = 0; i2 < busRealTimeResponse.buses.size(); i2++) {
                    RealTimeBusLineInfo realTimeBusLineInfo = busRealTimeResponse.buses.get(i2);
                    if (realTimeBusLineInfo != null && hashSet.contains(realTimeBusLineInfo.station)) {
                        ArrayList<RealTimeBusInfo> arrayList = realTimeBusLineInfo.trip;
                        if (arrayList != null) {
                            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                RealTimeBusInfo realTimeBusInfo = arrayList.get(i3);
                                if (realTimeBusInfo != null) {
                                    StringBuilder sb = new StringBuilder("deng--");
                                    sb.append(i3);
                                    sb.append("-剩余");
                                    sb.append(realTimeBusInfo.station_left);
                                    sb.append("站,");
                                    sb.append(realTimeBusInfo.arrival);
                                    sb.append("秒");
                                    azb.a(null, sb.toString());
                                    if (Integer.parseInt(realTimeBusInfo.station_left) <= 1 || Integer.parseInt(realTimeBusInfo.arrival) <= 60) {
                                        a(a.b);
                                        azb.a(null, "deng---快到站了，缩短轮询频率");
                                        if (this.a != null) {
                                            long a2 = (long) a.b;
                                            StringBuilder sb2 = new StringBuilder("deng--startRefresh realTime ");
                                            sb2.append(a2);
                                            sb2.append("ms later");
                                            azb.a(null, sb2.toString());
                                            if (this.a != null) {
                                                this.a.removeMessages(1);
                                                this.a.e = true;
                                                this.a.sendEmptyMessageDelayed(1, a2);
                                            }
                                        }
                                        return;
                                    }
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
                a(a.a);
            }
        }
    }

    public final void a() {
        azb.a(null, "deng--stopRefresh realTime");
        if (this.a != null) {
            this.a.removeMessages(1);
            this.a.e = false;
        }
    }

    public final void b() {
        azb.a(null, "deng--startRefresh realTime now");
        if (this.a != null) {
            this.a.removeMessages(1);
            this.a.e = true;
            this.a.sendEmptyMessage(1);
        }
    }
}
