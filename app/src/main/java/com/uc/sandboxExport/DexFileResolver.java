package com.uc.sandboxExport;

import android.os.Build.VERSION;

@Api
/* compiled from: ProGuard */
public class DexFileResolver {
    private static final boolean a = Switches.a;
    private static boolean b = false;

    private static native long nativeGetLoadDexTime();

    private static native long nativeLoadDexByFdOnL(int i);

    private static native Object nativeLoadDexByFdOnLAbove(int i);

    private static native void nativeSetNeedVerifyRawDex(boolean z);

    public static void loadloaderLibray() {
        if (!b && !b) {
            System.loadLibrary("servicedexloader");
            b = true;
        }
    }

    public static long loadDexByFdOnL(int i) {
        loadloaderLibray();
        if (VERSION.SDK_INT < 23) {
            return nativeLoadDexByFdOnL(i);
        }
        return 0;
    }

    public static Object loadDexByFdOnLAbove(int i) {
        loadloaderLibray();
        Object nativeLoadDexByFdOnLAbove = nativeLoadDexByFdOnLAbove(i);
        if (a) {
            new StringBuilder("mCookie: ").append(nativeLoadDexByFdOnLAbove);
        }
        return nativeLoadDexByFdOnLAbove;
    }

    public static long getLoadDextime() {
        return nativeGetLoadDexTime();
    }

    public static void setNeedVerifyRawDex(boolean z) {
        nativeSetNeedVerifyRawDex(z);
    }
}
