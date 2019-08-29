package android.support.v4.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;

public class BundleCompat {
    public static IBinder getBinder(Bundle bundle, String str) {
        if (VERSION.SDK_INT >= 18) {
            return BundleCompatJellybeanMR2.a(bundle, str);
        }
        return BundleCompatDonut.a(bundle, str);
    }

    public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
        if (VERSION.SDK_INT >= 18) {
            BundleCompatJellybeanMR2.a(bundle, str, iBinder);
        } else {
            BundleCompatDonut.a(bundle, str, iBinder);
        }
    }
}
