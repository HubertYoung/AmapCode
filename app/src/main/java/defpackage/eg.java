package defpackage;

import anetwork.channel.stat.NetworkStatCache$1;
import anetwork.channel.statist.StatisticData;
import com.alipay.sdk.util.h;
import java.util.Collections;
import java.util.Map;

/* renamed from: eg reason: default package */
/* compiled from: NetworkStatCache */
public final class eg implements ef {
    private Map<String, String> a;

    /* renamed from: eg$a */
    /* compiled from: NetworkStatCache */
    public static class a {
        public static eg a = new eg(0);
    }

    /* synthetic */ eg(byte b) {
        this();
    }

    private eg() {
        this.a = Collections.synchronizedMap(new NetworkStatCache$1(this));
    }

    public final void a(String str, StatisticData statisticData) {
        if (!cz.c(str)) {
            StringBuilder sb = new StringBuilder(48);
            sb.append("{\"oneWayTime\" : ");
            sb.append(statisticData.oneWayTime_ANet);
            sb.append(", \"totalSize\" : ");
            sb.append(statisticData.totalSize);
            sb.append(h.d);
            this.a.put(str, sb.toString());
        }
    }
}
