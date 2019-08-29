package defpackage;

import com.autonavi.jni.ae.gmap.gloverlay.GLRouteOverlay;
import java.util.HashMap;

/* renamed from: ti reason: default package */
/* compiled from: DrawMapRouteTools */
public final class ti {
    public static void a(int i, GLRouteOverlay gLRouteOverlay) {
        HashMap hashMap = new HashMap();
        for (int i2 = 0; i2 < 18; i2++) {
            int i3 = i2 + 3;
            int i4 = i - 2;
            if (i3 < i4) {
                hashMap.put(Integer.valueOf(i3), Integer.valueOf(0));
            } else if (i3 == i4) {
                hashMap.put(Integer.valueOf(i3), Integer.valueOf(1));
            } else if (i3 == i - 1) {
                hashMap.put(Integer.valueOf(i3), Integer.valueOf(2));
            } else if (i3 == i) {
                hashMap.put(Integer.valueOf(i3), Integer.valueOf(3));
            } else if (i3 == i + 1) {
                hashMap.put(Integer.valueOf(i3), Integer.valueOf(3));
            } else {
                int i5 = i + 2;
                if (i3 == i5) {
                    hashMap.put(Integer.valueOf(i3), Integer.valueOf(3));
                } else if (i3 > i5) {
                    hashMap.put(Integer.valueOf(i3), Integer.valueOf(5));
                }
            }
        }
        gLRouteOverlay.setShowNaviRouteNameCountMap(hashMap);
    }
}
