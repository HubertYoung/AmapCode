package com.alipay.streammedia.encode;

import android.os.Build.VERSION;
import android.util.Log;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class NativeIOMX {
    private static final String TAG = "NativeIOMX";
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };

    public native int NativeGetHandle(int i);

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (NativeIOMX.class) {
            if (!mIsLibLoaded && (VERSION.SDK_INT == 19 || VERSION.SDK_INT == 21 || VERSION.SDK_INT == 22 || VERSION.SDK_INT == 23)) {
                try {
                    Log.d(TAG, "Load iomx." + VERSION.SDK_INT);
                    System.loadLibrary("iomx." + VERSION.SDK_INT);
                    mIsLibLoaded = true;
                } catch (Throwable th) {
                }
            }
        }
    }

    public NativeIOMX() {
        loadLibrariesOnce(sLocalLibLoader);
    }

    public NativeIOMX(IjkLibLoader libLoader) {
        loadLibrariesOnce(libLoader);
    }
}
