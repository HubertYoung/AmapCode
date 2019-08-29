package defpackage;

import android.content.Context;
import com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager.Channel;

/* renamed from: eog reason: default package */
/* compiled from: QATestInfoAPI */
public final class eog {
    public static eog a;
    public static Context d;
    public boolean b = true;
    public int c = 0;

    public static synchronized eog a(Context context) {
        eog eog;
        synchronized (eog.class) {
            if (a == null) {
                a = new eog();
                d = context;
            }
            eog = a;
        }
        return eog;
    }

    private eog() {
    }

    public static Channel a() {
        if (!bno.a) {
            return Channel.UT_CHANNEL;
        }
        new eoi();
        if (eoi.b()) {
            return Channel.UT_CHANNEL;
        }
        return Channel.AMAP_CHANNEL;
    }

    public static boolean b() {
        if (!bno.a) {
            return false;
        }
        new eoi();
        if (!eoi.b()) {
            return true;
        }
        if (bno.a) {
            eof.a(d);
            eof.a();
        }
        return false;
    }

    public static String a(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        String replaceAll = str.replaceAll("[^一-龥]", "");
        return replaceAll.length() > 4 ? replaceAll.substring(0, 4) : replaceAll;
    }
}
