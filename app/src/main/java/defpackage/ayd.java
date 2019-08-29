package defpackage;

import java.util.concurrent.TimeUnit;

/* renamed from: ayd reason: default package */
/* compiled from: RouteCommuteGuideTipPolicy */
public final class ayd implements ayc {
    public final boolean a(aya aya) {
        boolean z;
        boolean z2 = false;
        boolean z3 = aya.a < 5;
        long currentTimeMillis = System.currentTimeMillis();
        if (aya.b != 0) {
            if (Math.abs(TimeUnit.DAYS.convert(currentTimeMillis, TimeUnit.MILLISECONDS) - TimeUnit.DAYS.convert(aya.b, TimeUnit.MILLISECONDS)) < 3) {
                z = false;
                if (z3 && z && a.a.b() != null) {
                    z2 = true;
                }
                StringBuilder sb = new StringBuilder("shouldShowGuideTip, result=");
                sb.append(z2);
                sb.append(", cancel times= ");
                sb.append(aya.a);
                sb.append(", exceed limit days=");
                sb.append(z);
                azb.a("RouteCommuteGuideTipPolicy", sb.toString());
                return z2;
            }
        }
        z = true;
        z2 = true;
        StringBuilder sb2 = new StringBuilder("shouldShowGuideTip, result=");
        sb2.append(z2);
        sb2.append(", cancel times= ");
        sb2.append(aya.a);
        sb2.append(", exceed limit days=");
        sb2.append(z);
        azb.a("RouteCommuteGuideTipPolicy", sb2.toString());
        return z2;
    }
}
