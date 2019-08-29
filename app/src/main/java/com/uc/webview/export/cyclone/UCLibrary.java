package com.uc.webview.export.cyclone;

import android.content.Context;
import android.webkit.ValueCallback;
import java.io.FileOutputStream;
import java.util.HashSet;

@Constant
/* compiled from: ProGuard */
public class UCLibrary {
    private static final String CLASS_IMPL = "com.uc.webview.export.internal.setup.UCLibraryLoaderImpl";
    private static final String DEX_FILE_DATA = "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";
    private static final long DEX_FILE_LENGTH = 2695;
    private static final String DEX_FILE_NAME = "d_2695_24354940.jar";
    private static final long DEX_FILE_TIME = 24354940;
    private static final HashSet<String> sLoadedSOFiles = new HashSet<>(2);

    private UCLibrary() {
    }

    public static void load(Context context, String str, ClassLoader classLoader) {
        load(context, str, classLoader, null);
    }

    public static void load(Context context, String str, ClassLoader classLoader, ValueCallback<String>... valueCallbackArr) {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (UCCyclone.loadLibraryCallback != null) {
            UCCyclone.loadLibraryCallback.onReceiveValue(str);
            return;
        }
        if (valueCallbackArr != null) {
            try {
                if (valueCallbackArr.length > 0 && valueCallbackArr[0] != null) {
                    valueCallbackArr[0].onReceiveValue(str);
                    return;
                }
            } catch (Throwable th2) {
                throw new UCKnownException(6016, th2);
            }
        }
        if (classLoader == null || UCLibrary.class.getClassLoader() == classLoader) {
            System.load(str);
            return;
        }
        throw new RuntimeException("systemLoadDelegate should be specified while loading so in non-cyclone module.");
    }
}
