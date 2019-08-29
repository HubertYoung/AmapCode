package com.uc.crashsdk;

import com.uc.crashsdk.a.a;
import java.io.File;

/* compiled from: ProGuard */
final class c implements Runnable {
    c() {
    }

    public final void run() {
        try {
            b.w = !new File(b.z()).exists();
            if (b.w || b.e) {
                b.b(b.F());
                b.e = false;
            }
        } catch (Exception e) {
            a.a(e, false);
        }
    }
}
