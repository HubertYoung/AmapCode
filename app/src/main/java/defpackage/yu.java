package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.network.util.NetworkReachability;

/* renamed from: yu reason: default package */
/* compiled from: NetworkProxy */
public final class yu implements bpb {
    private final bpb a;
    private final bpb b;

    public yu(Context context) {
        if (context == null) {
            throw new IllegalStateException("NetworkProxy context can not be null!");
        }
        this.a = new bof();
        this.b = new ys(context);
    }

    public final bpa a(bph bph) throws Exception {
        boolean z = false;
        if (bph != null && !TextUtils.isEmpty(bph.getUrl()) && (bph.getChannel() == 2 || (bph.getChannel() == 0 && yr.a(bph.getUrl())))) {
            z = true;
        }
        if (z) {
            return this.b.a(bph);
        }
        bpa a2 = this.a.a(bph);
        if (!(bph == null || bph.requestStatistics == null)) {
            bph.requestStatistics.b = NetworkReachability.c().toString();
        }
        return a2;
    }
}
