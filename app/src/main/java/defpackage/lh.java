package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: lh reason: default package */
/* compiled from: ResUtil */
public final class lh {
    public static String a(Context context, int i) {
        if (context == null) {
            context = AMapAppGlobal.getApplication();
        }
        return context.getResources().getString(i);
    }

    public static Configuration a() {
        return AMapAppGlobal.getTopActivity().getResources().getConfiguration();
    }
}
