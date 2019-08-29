package com.uc.webview.export.cyclone;

@Constant
/* compiled from: ProGuard */
public class UCDex implements Runnable {
    private static com.uc.webview.export.cyclone.service.UCDex getUCDex() {
        com.uc.webview.export.cyclone.service.UCDex uCDex = (com.uc.webview.export.cyclone.service.UCDex) UCService.initImpl(com.uc.webview.export.cyclone.service.UCDex.class);
        if (uCDex != null) {
            return uCDex;
        }
        throw new UCKnownException((int) ErrorCode.UCSERVICE_UCDEX_IMPL_NOT_FOUND, (String) "The implement of UCDex service is not registered.");
    }

    public void run() {
        getUCDex().run();
    }
}
