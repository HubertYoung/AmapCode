package defpackage;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.TimerTask;

/* renamed from: cko reason: default package */
/* compiled from: CloudConfig */
public final class cko extends cky {

    /* renamed from: cko$a */
    /* compiled from: CloudConfig */
    static class a implements defpackage.lo.a {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final GeoPoint a() {
            return LocationInstrument.getInstance().getLatestPosition(5);
        }

        public final GeoPoint b() {
            return LocationInstrument.getInstance().getCacheLatestPosition();
        }
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "CloudConfig";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        lo a2 = lo.a();
        a2.b = new Handler(Looper.getMainLooper());
        a2.a = new ln(Looper.getMainLooper());
        ln lnVar = a2.a;
        lnVar.e = new b();
        lnVar.b = new TimerTask() {
            public final void run() {
                ln.this.h.sendMessage(ln.this.h.obtainMessage(1));
            }
        };
        lnVar.a.schedule(lnVar.b, 3600000, 3600000);
        a2.a.a();
        if (a2.b != null) {
            a2.b.post(new Runnable() {
                public final void run() {
                    HashSet<String> hashSet;
                    if (lo.this.c != null && lo.this.c.size() > 0) {
                        synchronized (lo.this) {
                            hashSet = new HashSet<>(lo.this.c.keySet());
                        }
                        for (String str : hashSet) {
                            String a2 = lo.this.a(str);
                            List<lp> list = (List) lo.this.c.get(str);
                            if (list != null) {
                                for (lp lpVar : list) {
                                    if (lpVar != null) {
                                        lpVar.onConfigResultCallBack(4, a2);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        } else if (bno.a) {
            throw new InvalidParameterException("云控Handler未在Looper初始化");
        } else {
            AMapLog.logErrorNative(AMapLog.GROUP_COMMON, "P0080", "CloudConfigService", "handler is null,callCacheDataListeners");
        }
        lo.a((defpackage.lo.a) new a(0));
    }
}
