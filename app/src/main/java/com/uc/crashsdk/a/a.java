package com.uc.crashsdk.a;

import com.uc.crashsdk.p;

/* compiled from: ProGuard */
public final class a {
    public static void a(Throwable th, boolean z) {
        if (z || p.G()) {
            th.printStackTrace();
        }
    }
}
