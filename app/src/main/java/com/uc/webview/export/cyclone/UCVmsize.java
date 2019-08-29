package com.uc.webview.export.cyclone;

import android.content.Context;

/* compiled from: ProGuard */
public class UCVmsize implements Runnable {
    private static final String TAG = "UCVmsize";
    private Context mCtx;

    public UCVmsize(Context context) {
        this.mCtx = context;
    }

    public void run() {
        com.uc.webview.export.cyclone.service.UCVmsize uCVmsize = (com.uc.webview.export.cyclone.service.UCVmsize) UCService.initImpl(com.uc.webview.export.cyclone.service.UCVmsize.class);
        if (uCVmsize != null) {
            try {
                long saveChromiumReservedSpace = uCVmsize.saveChromiumReservedSpace(this.mCtx);
                UCLogger create = UCLogger.create("i", TAG);
                if (create != null) {
                    create.print("saveChromiumReservedSpace ok with bytes:".concat(String.valueOf(saveChromiumReservedSpace)), new Throwable[0]);
                }
            } catch (Exception e) {
                UCLogger create2 = UCLogger.create("w", TAG);
                if (create2 != null) {
                    create2.print("saveChromiumReservedSpace error", e);
                }
            }
        }
    }
}
