package defpackage;

import android.text.TextUtils;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import java.util.Iterator;
import java.util.Vector;

/* renamed from: ehj reason: default package */
/* compiled from: TraceRecordManager */
public class ehj implements egw {
    private static volatile ehj b;
    public Vector<a> a = new Vector<>();

    /* renamed from: ehj$a */
    /* compiled from: TraceRecordManager */
    public interface a {
        void a(TraceStatistics traceStatistics, RideTraceHistory rideTraceHistory);
    }

    public final boolean a(OrderState orderState, OrderInfo orderInfo, int i) {
        if (orderInfo == null) {
            return false;
        }
        if (!orderInfo.hasNetFailed) {
            if (orderInfo.extraData instanceof RideState) {
                RideState rideState = (RideState) orderInfo.extraData;
                String b2 = ehs.b("share_bike_order_id");
                String str = "";
                if (rideState != null && rideState.result) {
                    if (!TextUtils.isEmpty(rideState.orderId)) {
                        str = rideState.orderId;
                    }
                    boolean parseBoolean = Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"));
                    if (parseBoolean && rideState.status == 1 && TextUtils.equals(b2, str) && !ehl.a(AMapPageUtil.getAppContext()).a) {
                        ehl.a(AMapPageUtil.getAppContext()).a(b2);
                    }
                    if (parseBoolean && rideState.status == 0 && TextUtils.equals(b2, str)) {
                        eht.e();
                        if (this.a.size() > 0) {
                            a((ecy) new ecy() {
                                public final void a(TraceStatistics traceStatistics, RideTraceHistory rideTraceHistory) {
                                    Iterator it = ehj.this.a.iterator();
                                    while (it.hasNext()) {
                                        ((a) it.next()).a(traceStatistics, rideTraceHistory);
                                    }
                                }
                            });
                        } else {
                            a((ecy) null);
                        }
                    } else if (rideState.status == 3) {
                        eht.e();
                        if (ehl.a(AMapPageUtil.getAppContext()).a) {
                            ehl.a(AMapPageUtil.getAppContext()).b(ehl.a(AMapPageUtil.getAppContext()).b());
                        }
                        egj.a().a(false);
                    } else if (parseBoolean && !TextUtils.equals(b2, str)) {
                        ehl.a(AMapPageUtil.getAppContext()).a();
                        a((ecy) new ecy() {
                            public final void a(TraceStatistics traceStatistics, RideTraceHistory rideTraceHistory) {
                                Iterator it = ehj.this.a.iterator();
                                while (it.hasNext()) {
                                    ((a) it.next()).a(traceStatistics, rideTraceHistory);
                                }
                            }
                        });
                        String b3 = ehs.b("share_bike_order_id");
                        if (!TextUtils.isEmpty(b3)) {
                            String b4 = ehl.a(AMapPageUtil.getAppContext()).b();
                            if (!TextUtils.equals(b3, b4) && !TextUtils.isEmpty(b4)) {
                                ehl.a(AMapPageUtil.getAppContext()).a();
                            }
                        }
                        if (!ehl.a(AMapPageUtil.getAppContext()).a) {
                            ehl.a(AMapPageUtil.getAppContext()).a(str);
                        }
                    }
                }
            }
        } else if (i == 60) {
            a((ecy) null);
        }
        return false;
    }

    public static ehj a() {
        if (b == null) {
            synchronized (ehj.class) {
                try {
                    b = new ehj();
                }
            }
        }
        return b;
    }

    private ehj() {
    }

    private static void a(ecy ecy) {
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        aww aww = (aww) defpackage.esb.a.a.a(aww.class);
        if (aww != null) {
            String simpleName = aww.a().a(1).getSimpleName();
            if (!ShareRidingMapPage.class.getSimpleName().equals(topPageClass.getSimpleName()) && !simpleName.equals(topPageClass.getSimpleName())) {
                ehl.a(AMapPageUtil.getAppContext()).a();
                ehm.a(ecy, true, false);
            }
        }
    }
}
