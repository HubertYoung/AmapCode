package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;

/* renamed from: dhh reason: default package */
/* compiled from: BannerPreference */
public class dhh {
    private static volatile MapSharePreference a;

    public static MapSharePreference a() {
        if (a == null) {
            synchronized (dhh.class) {
                try {
                    if (a == null) {
                        a = new MapSharePreference((String) "bannerlist");
                    }
                }
            }
        }
        return a;
    }
}
