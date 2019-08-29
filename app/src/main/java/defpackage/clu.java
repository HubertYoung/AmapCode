package defpackage;

import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.jni.alc.inter.IALCCloudStrategy;

/* renamed from: clu reason: default package */
/* compiled from: AMapLogCloudStrategy */
public final class clu implements IALCCloudStrategy {
    public String a = "";

    public clu(String str) {
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
