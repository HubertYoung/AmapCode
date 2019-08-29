package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: sy reason: default package */
/* compiled from: OfflinePreference */
public class sy {
    private static volatile MapSharePreference a;

    public static MapSharePreference a() {
        if (a == null) {
            synchronized (sy.class) {
                try {
                    if (a == null) {
                        a = new MapSharePreference(SharePreferenceName.SharedPreferences);
                    }
                }
            }
        }
        return a;
    }
}
