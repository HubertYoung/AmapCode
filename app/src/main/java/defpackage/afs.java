package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

/* renamed from: afs reason: default package */
/* compiled from: AmapTelephonyManager */
public class afs {
    private static volatile afs b;
    public TelephonyManager a;

    public static afs a(@NonNull Context context) {
        if (b == null) {
            synchronized (afs.class) {
                try {
                    if (b == null) {
                        b = new afs(context);
                    }
                }
            }
        }
        return b;
    }

    private afs(Context context) {
        this.a = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
    }
}
