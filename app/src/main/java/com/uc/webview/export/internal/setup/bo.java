package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.UCAsyncTask.b;
import com.uc.webview.export.internal.setup.UCAsyncTask.c;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.uc.startup.StartupTrace;

/* compiled from: ProGuard */
final class bo implements ValueCallback<u> {
    final /* synthetic */ q a;
    final /* synthetic */ bm b;

    bo(bm bmVar, q qVar) {
        this.b = bmVar;
        this.a = qVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        StartupTrace.traceEvent("StandardSetupTask.runInternal LibraryTask");
        this.b.b.callback(UCCore.OPTION_LOAD_KERNEL_TYPE);
        bl blVar = this.b.b;
        blVar.getClass();
        bl blVar2 = this.b.b;
        blVar2.getClass();
        bl blVar3 = this.b.b;
        blVar3.getClass();
        ((r) ((r) ((r) ((r) ((r) ((r) ((r) ((r) ((r) new r().invoke(10001, this.b.b)).setOptions(this.b.b.mOptions)).setUCM(this.a.mUCM)).setClassLoader(this.a.mCL)).setSdkShellClassLoader(this.a.mShellCL)).onEvent("stat", new a())).onEvent(LogCategory.CATEGORY_EXCEPTION, new b())).onEvent(AudioUtils.CMDSTOP, new c())).onEvent("success", new bp(this))).start();
    }
}
