package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.CookieSyncManager;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;

/* compiled from: ProGuard */
public final class bs extends t {
    public final void run() {
        try {
            CookieSyncManager.createInstance((Context) getOption(UCCore.OPTION_CONTEXT));
        } catch (RuntimeException unused) {
        }
        callback("setup");
        callback(UCCore.OPTION_LOAD_KERNEL_TYPE);
        SDKFactory.invoke(10021, Integer.valueOf(2));
        UCMRunningInfo uCMRunningInfo = new UCMRunningInfo(getContext(), null, null, null, false, false, null, 2, false, 0);
        setLoadedUCM(uCMRunningInfo);
        callback("init");
        callback(FunctionSupportConfiger.SWITCH_TAG);
        WaStat.stat((String) IWaStat.KEY_SYSTEM_SETUP_PV);
    }
}
