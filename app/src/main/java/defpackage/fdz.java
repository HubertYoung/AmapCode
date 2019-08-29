package defpackage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: fdz reason: default package */
/* compiled from: AbstractFilterManager */
public abstract class fdz implements fdy {
    protected final List<fdh> a = new LinkedList();
    protected final List<fdg> b = new LinkedList();

    public final void a(fdh fdh) {
        this.a.add(fdh);
    }

    public final void a(fdg fdg) {
        this.b.add(fdg);
    }

    public final void a(String str, fdf fdf) {
        boolean b2 = fdd.b(str);
        Iterator<fdh> it = this.a.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            fdh next = it.next();
            if (!b2) {
                if (!str.equals(next.a())) {
                    continue;
                } else {
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b((String) "mtopsdk.AbstractFilterManager", fdf.h, "[start]jump to beforeFilter:".concat(String.valueOf(str)));
                    }
                    b2 = true;
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            String b3 = next.b(fdf);
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                String str2 = fdf.h;
                StringBuilder sb = new StringBuilder("[start]execute BeforeFilter: ");
                sb.append(next.a());
                sb.append(",time(ms)= ");
                sb.append(System.currentTimeMillis() - currentTimeMillis);
                TBSdkLog.a((String) "mtopsdk.AbstractFilterManager", str2, sb.toString());
            }
            if ("STOP" == b3) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    String str3 = fdf.h;
                    StringBuilder sb2 = new StringBuilder("[start]execute BeforeFilter: ");
                    sb2.append(next.a());
                    sb2.append(",result=");
                    sb2.append(b3);
                    TBSdkLog.b((String) "mtopsdk.AbstractFilterManager", str3, sb2.toString());
                    return;
                }
            }
        }
    }

    public final void a(fdf fdf) {
        String str = null;
        boolean b2 = fdd.b(str);
        Iterator<fdg> it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            fdg next = it.next();
            if (!b2) {
                if (!str.equals(next.a())) {
                    continue;
                } else {
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        String str2 = fdf.h;
                        StringBuilder sb = new StringBuilder("[callback]jump to afterFilter:");
                        sb.append(str);
                        TBSdkLog.b((String) "mtopsdk.AbstractFilterManager", str2, sb.toString());
                    }
                    b2 = true;
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            String a2 = next.a(fdf);
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                String str3 = fdf.h;
                StringBuilder sb2 = new StringBuilder("[callback]execute AfterFilter: ");
                sb2.append(next.a());
                sb2.append(",time(ms)= ");
                sb2.append(System.currentTimeMillis() - currentTimeMillis);
                TBSdkLog.a((String) "mtopsdk.AbstractFilterManager", str3, sb2.toString());
            }
            if ("STOP" == a2) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    String str4 = fdf.h;
                    StringBuilder sb3 = new StringBuilder("[callback]execute AfterFilter: ");
                    sb3.append(next.a());
                    sb3.append(",result=");
                    sb3.append(a2);
                    TBSdkLog.b((String) "mtopsdk.AbstractFilterManager", str4, sb3.toString());
                    return;
                }
            }
        }
    }
}
