package defpackage;

import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.jni.alc.inter.IALCCloudStrategy;

/* renamed from: clv reason: default package */
/* compiled from: AMapLogCloudStrategyLotuspool */
public final class clv implements IALCCloudStrategy {
    public final String cloudStrategy() {
        return "{\n\n\t\"upload\": {\n\n\t\t\"P1\": {\n\n\t\t\t\"timing\": 7,\n\n\t\t\t\"network\": 1,\n\n\t\t\t\"size\": 20480\n\n\t\t},\n\n\t\t\"P2\": {\n\n\t\t\t\"timing\": 7,\n\n\t\t\t\"network\": 1,\n\n\t\t\t\"size\": 20480\n\n\t\t},\n\t\t\"storge\": \"P2\"\n\t}\n\n}";
    }

    public final int currentNetworkStatus() {
        switch (NetworkReachability.c()) {
            case G2:
                return 3;
            case G3:
                return 4;
            case G4:
                return 5;
            default:
                return 1;
        }
    }
}
