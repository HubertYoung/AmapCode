package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;
import com.uc.webview.export.internal.SDKFactory;

/* renamed from: pn reason: default package */
/* compiled from: MitVuiHelloGaodeModel */
public final class pn extends bgd {
    public final String a() {
        return "helloGaode";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb.b);
        tq.b("NaviMonitor", "MitVuiHelloGaodeModel", sb.toString());
        if (!NetworkReachability.b()) {
            d.a.a(bgb.a, 10008, (String) null);
            return true;
        }
        String str = bgb.h;
        if (TextUtils.isEmpty(str)) {
            bfq bfq = a.a;
            StringBuilder sb2 = new StringBuilder(" cmdText Empty taskId=");
            sb2.append(bgb.f);
            bfp.a(bfq, 2, sb2.toString());
            d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
            return false;
        }
        if (str.contains("[") && str.contains("]")) {
            str = str.substring(str.indexOf("]"), str.length());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(d.a.a(Long.valueOf(1), (String) "defaultText"));
        d.a.a(bgb.a, 10000, sb3.toString());
        return true;
    }
}
