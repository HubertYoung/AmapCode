package defpackage;

import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.jni.alc.inter.IALCCloudStrategy;

/* renamed from: cls reason: default package */
/* compiled from: AMapLogAllInCloudStrategy */
public final class cls implements IALCCloudStrategy {
    public String a = "";

    public cls(String str) {
        this.a = str;
    }

    public final int currentNetworkStatus() {
        switch (NetworkReachability.c()) {
            case G2:
                return 3;
            case G3:
                return 4;
            case G4:
                return 5;
            case WIFI:
                return 2;
            default:
                return 1;
        }
    }

    public final String cloudStrategy() {
        return this.a;
    }
}
