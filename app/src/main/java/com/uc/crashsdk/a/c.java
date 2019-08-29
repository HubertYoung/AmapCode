package com.uc.crashsdk.a;

import android.util.Log;
import com.uc.crashsdk.JNIBridge;
import com.uc.crashsdk.b;
import com.uc.crashsdk.p;

/* compiled from: ProGuard */
public final class c {
    public static void a(String str) {
        if (p.G()) {
            Log.d("crashsdk", str);
        }
    }

    public static void b(String str) {
        if (p.G()) {
            Log.i("crashsdk", str);
        }
    }

    public static void c(String str) {
        if (p.G()) {
            Log.w("crashsdk", str);
        }
    }

    public static void a(String str, String str2) {
        if (p.G()) {
            Log.e(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (b.d) {
            JNIBridge.nativeLog(5, str, str2);
        } else {
            Log.w(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (b.d) {
            JNIBridge.nativeLog(6, str, str2);
        } else {
            Log.e(str, str2);
        }
    }
}
