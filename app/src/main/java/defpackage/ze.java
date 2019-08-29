package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: ze reason: default package */
/* compiled from: CdnLogSwitcher */
public final class ze {
    private static ze b;
    public boolean a;

    public static synchronized ze a() {
        ze zeVar;
        synchronized (ze.class) {
            try {
                if (b == null) {
                    b = new ze(aaf.a());
                }
                zeVar = b;
            }
        }
        return zeVar;
    }

    private ze(Context context) {
        this.a = new MapSharePreference(context, SharePreferenceName.SharedPreferences).getBooleanValue("cdn_log_switch", false);
    }
}
