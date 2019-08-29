package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.UCAsyncTask.b;
import com.uc.webview.export.internal.setup.UCAsyncTask.c;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class bm implements ValueCallback<q> {
    final /* synthetic */ bx a;
    final /* synthetic */ bl b;

    bm(bl blVar, bx bxVar) {
        this.b = blVar;
        this.a = bxVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        q qVar = (q) obj;
        StartupTrace.traceEvent("StandardSetupTask.runInternal envTask");
        this.b.callback("setup");
        if (!j.c((Boolean) this.b.getOption(UCCore.OPTION_ENABLE_LOAD_CLASS))) {
            UCCore.getCurrentLoadClassTask().setUCM(qVar.mUCM).setClassLoader(qVar.mCL).setup(UCCore.OPTION_SETUP_THREAD_PRIORITY, Integer.valueOf(Thread.currentThread().getPriority())).onEvent("success", new bn(this)).start();
        }
        bl blVar = this.b;
        blVar.getClass();
        bl blVar2 = this.b;
        blVar2.getClass();
        bl blVar3 = this.b;
        blVar3.getClass();
        ((u) ((u) ((u) ((u) ((u) ((u) ((u) ((u) new u().invoke(10001, this.b)).setOptions(this.b.mOptions)).setUCM(qVar.mUCM)).setClassLoader(qVar.mCL)).onEvent("stat", new a())).onEvent(LogCategory.CATEGORY_EXCEPTION, new b())).onEvent(AudioUtils.CMDSTOP, new c())).onEvent("success", new bo(this, qVar))).start();
    }
}
