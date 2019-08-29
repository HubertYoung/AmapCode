package defpackage;

import android.util.Base64;
import com.autonavi.bundle.routecommon.model.RouteType;

/* renamed from: aci reason: default package */
/* compiled from: ETripDataUtil */
public final class aci {

    /* renamed from: aci$1 reason: invalid class name */
    /* compiled from: ETripDataUtil */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a = new int[RouteType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.autonavi.bundle.routecommon.model.RouteType[] r0 = com.autonavi.bundle.routecommon.model.RouteType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.aci.AnonymousClass1.<clinit>():void");
        }
    }

    public static byte[] a(String str) {
        try {
            return Base64.decode(str.getBytes(), 0);
        } catch (Exception e) {
            eao.a("ETripDataUtil", "original data base64-decode error, exception: ", e);
            return null;
        }
    }
}
