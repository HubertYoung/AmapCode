package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;

/* renamed from: dhi reason: default package */
/* compiled from: CustomVoicePreference */
public class dhi {
    private static volatile MapSharePreference a;

    public static MapSharePreference a() {
        if (a == null) {
            synchronized (dhi.class) {
                try {
                    if (a == null) {
                        a = new MapSharePreference((String) "CUSTOM_VOICE");
                    }
                }
            }
        }
        return a;
    }
}
