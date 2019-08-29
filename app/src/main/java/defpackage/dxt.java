package defpackage;

import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import java.util.Comparator;

/* renamed from: dxt reason: default package */
/* compiled from: Comparators */
public final class dxt {

    /* renamed from: dxt$a */
    /* compiled from: Comparators */
    public static class a implements Comparator<RealTimeBusAndStationMatchup> {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = (RealTimeBusAndStationMatchup) obj;
            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup2 = (RealTimeBusAndStationMatchup) obj2;
            if (realTimeBusAndStationMatchup.isFollow() == realTimeBusAndStationMatchup2.isFollow()) {
                if (!realTimeBusAndStationMatchup.mBean.isRealTimeBus() || !realTimeBusAndStationMatchup2.mBean.isRealTimeBus()) {
                    if (realTimeBusAndStationMatchup.mBean.isRealTimeBus()) {
                        return -1;
                    }
                    if (!realTimeBusAndStationMatchup2.mBean.isRealTimeBus()) {
                        return 0;
                    }
                } else if (realTimeBusAndStationMatchup.mTrip != null && realTimeBusAndStationMatchup2.mTrip != null) {
                    return realTimeBusAndStationMatchup.mTrip.arrival - realTimeBusAndStationMatchup2.mTrip.arrival;
                } else {
                    if (realTimeBusAndStationMatchup.mTrip != null) {
                        return -1;
                    }
                }
            } else if (realTimeBusAndStationMatchup.isFollow()) {
                return -1;
            }
            return 1;
        }
    }

    /* renamed from: dxt$b */
    /* compiled from: Comparators */
    public static class b implements Comparator<dyi> {
        public double a;
        public double b;

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            dyi dyi = (dyi) obj;
            dyi dyi2 = (dyi) obj2;
            return Float.compare(cfe.a(this.a, this.b, dyi.d, dyi.c), cfe.a(this.a, this.b, dyi2.d, dyi2.c));
        }

        public b(double d, double d2) {
            this.a = d;
            this.b = d2;
        }
    }
}
