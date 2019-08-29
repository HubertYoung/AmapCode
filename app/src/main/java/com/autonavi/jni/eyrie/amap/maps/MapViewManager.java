package com.autonavi.jni.eyrie.amap.maps;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.jni.eyrie.amap.tbt.TextureLoader;

public class MapViewManager {
    public static native void nativeAddTextureLoader(TextureLoader textureLoader, int i);

    private static native int nativeDestroyMapView(int i);

    private static native int nativeNewMapView(int i);

    private static native boolean nativeOnClick(int i, float f, float f2);

    private static native void nativeOnDoubleClick(int i, float f, float f2);

    private static native void nativeOnLongPress(int i, float f, float f2);

    /* access modifiers changed from: private */
    public static native void nativeOnMotionFinish(int i);

    private static native void nativeOnMotionStart(int i);

    private static native void nativeOnTouchEvent(int i, float f, float f2, int i2);

    public static native void nativeRemoveTextureLoader(TextureLoader textureLoader);

    private static native int nativeSafeDestroyMapView(int i);

    private static native void nativeSetBackground();

    private static native void nativeSetForeground();

    private static native void nativeSetGlyphLoaderFactory(IGlyphLoaderFactory iGlyphLoaderFactory);

    private static native void nativeUninit();

    public static int newMapView(int i) {
        return nativeNewMapView(i);
    }

    public static int destroyMapView(int i) {
        return nativeDestroyMapView(i);
    }

    public static int safeDestroyMapView(int i) {
        return nativeSafeDestroyMapView(i);
    }

    public static boolean onClick(int i, float f, float f2) {
        return nativeOnClick(i, f, f2);
    }

    public static void onDoubleClick(int i, float f, float f2) {
        nativeOnDoubleClick(i, f, f2);
    }

    public static void onLongPress(int i, float f, float f2) {
        nativeOnLongPress(i, f, f2);
    }

    public static void onMotionStart(int i) {
        nativeOnMotionStart(i);
    }

    public static void onMotionFinish(final int i) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    MapViewManager.nativeOnMotionFinish(i);
                }
            });
        } else {
            nativeOnMotionFinish(i);
        }
    }

    public static void onTouchEvent(int i, float f, float f2, int i2) {
        nativeOnTouchEvent(i, f, f2, i2);
    }

    public static void setForeground() {
        nativeSetForeground();
    }

    public static void setBackground() {
        nativeSetBackground();
    }

    public static void uninit() {
        nativeUninit();
    }

    public static void setGlyphLoaderFactory(IGlyphLoaderFactory iGlyphLoaderFactory) {
        nativeSetGlyphLoaderFactory(iGlyphLoaderFactory);
    }

    public static void addTextureLoader(TextureLoader textureLoader, int i) {
        nativeAddTextureLoader(textureLoader, i);
    }

    public static void removeTextureLoader(TextureLoader textureLoader) {
        nativeRemoveTextureLoader(textureLoader);
    }
}
