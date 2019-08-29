package defpackage;

import android.text.TextUtils;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.bicycle.param.ShareBikeRideStateRequest;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: egj reason: default package */
/* compiled from: RidingStatusPollManager */
public final class egj implements com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a {
    /* access modifiers changed from: private */
    public static egj b;
    public int a;
    private Set<b> c = new HashSet();
    private Lock d = new ReentrantLock();
    private volatile RideState e;
    private String f;
    private String g;
    private boolean h;
    private a i = new a();

    /* renamed from: egj$a */
    /* compiled from: RidingStatusPollManager */
    static class a extends Thread {
        volatile boolean a;
        private ShareBikeRideStateRequest b;
        private Object c = new Object();
        private volatile boolean d = false;
        /* access modifiers changed from: private */
        public boolean e = true;

        public final void run() {
            super.run();
            while (this.e) {
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("PollThread mThreadAlive --->");
                    sb.append(this.e);
                    sb.append(" mParamEntity=");
                    sb.append(this.b);
                    sb.append("orderId --> ");
                    sb.append(this.b == null ? "mParamEntity == null" : this.b.f);
                    eao.e("tylorvan", sb.toString());
                }
                if (this.a && this.b != null) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    double d2 = 0.0d;
                    double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
                    if (latestPosition != null) {
                        d2 = latestPosition.getLatitude();
                    }
                    ShareBikeRideStateRequest shareBikeRideStateRequest = new ShareBikeRideStateRequest();
                    shareBikeRideStateRequest.b = this.b.b;
                    shareBikeRideStateRequest.c = String.valueOf(longitude);
                    shareBikeRideStateRequest.d = String.valueOf(d2);
                    shareBikeRideStateRequest.e = this.b.e;
                    shareBikeRideStateRequest.f = this.b.f;
                    if (!TextUtils.isEmpty(shareBikeRideStateRequest.b)) {
                        egu.a(shareBikeRideStateRequest, (com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a) egj.b);
                        eao.e("tylorvan", "PollThread requestRideState");
                    }
                }
                try {
                    synchronized (this.c) {
                        this.d = true;
                        this.c.wait(StatisticConfig.MIN_UPLOAD_INTERVAL);
                        this.d = false;
                    }
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }

        static /* synthetic */ void a(a aVar, boolean z) {
            eao.e("tylorvan", "source thread setLoopAlive".concat(String.valueOf(z)));
            aVar.a = z;
            if (z && aVar.d) {
                synchronized (aVar.c) {
                    aVar.c.notifyAll();
                }
            }
        }

        static /* synthetic */ void a(a aVar, String str, double d2, double d3, String str2, String str3) {
            StringBuilder sb = new StringBuilder("source ");
            sb.append(str);
            sb.append(LocationParams.PARA_FLP_AUTONAVI_LON);
            sb.append(d2);
            sb.append("lat");
            sb.append(d3);
            sb.append("code");
            sb.append(str2);
            sb.append(" orderId ");
            sb.append(str3);
            eao.e("tylorvan", sb.toString());
            aVar.b = new ShareBikeRideStateRequest();
            aVar.b.b = str;
            aVar.b.c = String.valueOf(d2);
            aVar.b.d = String.valueOf(d3);
            aVar.b.e = str2;
            aVar.b.f = str3;
        }
    }

    /* renamed from: egj$b */
    /* compiled from: RidingStatusPollManager */
    public interface b {
    }

    private egj() {
        this.i.setName(getClass().getName());
        this.i.start();
        this.a = 0;
    }

    public static synchronized egj a() {
        egj egj;
        synchronized (egj.class) {
            try {
                if (b == null) {
                    b = new egj();
                }
                egj = b;
            }
        }
        return egj;
    }

    public final synchronized void b() {
        this.i.e = false;
        this.i = null;
        this.c = null;
        b = null;
    }

    public final RideState c() {
        this.d.lock();
        RideState rideState = this.e;
        this.d.unlock();
        return rideState;
    }

    public final synchronized void a(boolean z) {
        eao.f("tylorvan", "RidingStatusPollManager source setLoopAlive".concat(String.valueOf(z)));
        if (TextUtils.isEmpty(this.f)) {
            this.f = ehs.b("share_bike_order_id");
        }
        if (TextUtils.isEmpty(this.g)) {
            this.g = ehs.b("share_bike_token_id");
        }
        if (this.i != null) {
            a.a(this.i, z);
        }
    }

    public final void a(String str, float f2, float f3, String str2, String str3) {
        if (this.i != null) {
            a.a(this.i, str, (double) f2, (double) f3, str2, str3);
        }
    }

    public final void d() {
        this.e = null;
        this.h = false;
    }

    public final void a(BaseNetResult baseNetResult) {
        Object obj;
        String str;
        String str2;
        int i2;
        this.e = (RideState) baseNetResult;
        StringBuilder sb = new StringBuilder("PollThread requestFinish ---> mFailedTime");
        sb.append(this.a);
        sb.append(" result: ");
        if (this.e == null) {
            obj = null;
        } else {
            obj = Boolean.valueOf(this.e.result);
        }
        sb.append(obj);
        sb.append("  errorcode ---> ");
        if (this.e == null) {
            str = "null";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.e.errorCode);
            str = sb2.toString();
        }
        sb.append(str);
        sb.append("  status: ");
        if (this.e == null) {
            str2 = "null";
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.e.status);
            str2 = sb3.toString();
        }
        sb.append(str2);
        eao.e("tylorvan", sb.toString());
        boolean z = true;
        this.h = true;
        boolean z2 = this.e != null && this.e.result;
        this.d.lock();
        if (z2) {
            i2 = 0;
        } else {
            i2 = this.a + 1;
        }
        this.a = i2;
        HashSet<b> hashSet = new HashSet<>();
        hashSet.addAll(this.c);
        if (hashSet.size() > 0) {
            for (b bVar : hashSet) {
                if (bVar != null) {
                    if (z2) {
                        try {
                            RideState rideState = this.e;
                        } catch (Exception e2) {
                            StringBuilder sb4 = new StringBuilder("requestSuccess Exception e ---> ");
                            sb4.append(e2.getMessage());
                            eao.e("tylorvan", sb4.toString());
                            if (e2.getStackTrace() != null && e2.getStackTrace().length > 0) {
                                StringBuilder sb5 = new StringBuilder("requestSuccess  ---> ");
                                sb5.append(e2.getStackTrace()[0]);
                                eao.e("tylorvan", sb5.toString());
                            }
                        }
                    } else {
                        try {
                            RideState rideState2 = this.e;
                        } catch (Exception e3) {
                            StringBuilder sb6 = new StringBuilder("requestFailed Exception e ---> ");
                            sb6.append(e3.getMessage());
                            eao.e("tylorvan", sb6.toString());
                            e3.printStackTrace();
                        }
                    }
                }
            }
        }
        RideState rideState3 = this.e;
        OrderInfo orderInfo = new OrderInfo();
        if (rideState3 == null) {
            orderInfo.hasNetFailed = true;
        } else {
            orderInfo.hasNetFailed = false;
            orderInfo.hasUnfinishOrder = rideState3.status == 1 || rideState3.status == 2;
            orderInfo.orderId = rideState3.orderId;
            orderInfo.bikeId = rideState3.bikeId;
            orderInfo.status = rideState3.status;
            orderInfo.dataSource = 2;
            orderInfo.accountToken = rideState3.accountToken;
            orderInfo.accountUserid = rideState3.accountUserid;
            orderInfo.accountAppkey = rideState3.accountAppkey;
            orderInfo.extraData = rideState3;
        }
        egx.a().a(orderInfo);
        if (this.e != null && this.e.result) {
            if (this.e.status != 1) {
                z = false;
            }
            ehs.a((String) "share_bike_riding_status_id", String.valueOf(z));
            String str3 = this.e.orderId;
            if (!TextUtils.isEmpty(str3) && !TextUtils.equals(this.f, str3)) {
                this.f = str3;
                ehs.a(this.f);
            }
            String str4 = this.e.accountToken;
            if (!TextUtils.isEmpty(str4) && !TextUtils.equals(this.g, str4)) {
                this.g = str4;
                ehs.a((String) "share_bike_token_id", this.g);
            }
        }
        if (this.i != null && !this.i.a) {
            this.e = null;
            this.h = false;
        }
        this.d.unlock();
    }
}
