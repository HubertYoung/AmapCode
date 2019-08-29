package com.uc.webview.export.internal;

import com.uc.webview.export.internal.setup.UCSetupException;

/* compiled from: ProGuard */
final class c implements Runnable {
    c() {
    }

    public final void run() {
        while (true) {
            try {
                Runnable runnable = (Runnable) d.a.poll();
                if (runnable != null) {
                    runnable.run();
                } else {
                    return;
                }
            } catch (Exception e) {
                d.a.clear();
                d.b = new UCSetupException(4008, (Throwable) e);
                return;
            }
        }
    }
}
