package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: feq reason: default package */
/* compiled from: DefaultMtopCallback */
public class feq implements b, c, d {
    public void onHeader(fev fev, Object obj) {
        if (fev != null && TBSdkLog.a(LogEnable.DebugEnable)) {
            String str = fev.a;
            StringBuilder sb = new StringBuilder("[onHeader]");
            sb.append(fev.toString());
            TBSdkLog.a((String) "mtopsdk.DefaultMtopCallback", str, sb.toString());
        }
    }

    public void onFinished(feu feu, Object obj) {
        if (feu != null && feu.a != null && TBSdkLog.a(LogEnable.DebugEnable)) {
            String str = feu.b;
            StringBuilder sb = new StringBuilder("[onFinished]");
            sb.append(feu.a.toString());
            TBSdkLog.a((String) "mtopsdk.DefaultMtopCallback", str, sb.toString());
        }
    }
}
