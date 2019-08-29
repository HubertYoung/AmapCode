package com.uc.webview.export.business.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.business.BusinessWrapper;
import com.uc.webview.export.business.a.d;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.utility.SetupTask;

/* compiled from: ProGuard */
final class h implements ValueCallback<BaseSetupTask> {
    final /* synthetic */ a a;

    h(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        BaseSetupTask baseSetupTask = (BaseSetupTask) obj;
        String a2 = a.a;
        StringBuilder sb = new StringBuilder("mSuccessCallback ");
        sb.append(baseSetupTask.toString());
        sb.append(" init type: ");
        sb.append(baseSetupTask.getInitType());
        Log.d(a2, sb.toString());
        this.a.g.f = String.valueOf(this.a.g.a.getMilis());
        this.a.g.g = String.valueOf(this.a.g.a.getMilisCpu());
        if (((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue() != 2) {
            this.a.c.a(d.h);
            a.a(this.a, UCCore.EVENT_INIT_CORE_SUCCESS, baseSetupTask);
        } else {
            this.a.c.a(d.i);
        }
        if (UCCore.BUSINESS_INIT_BY_NEW_CORE_ZIP_FILE.equals(baseSetupTask.getInitType())) {
            o.a((String) this.a.getOption(UCCore.OPTION_BUSINESS_DECOMPRESS_ROOT_PATH), (String) this.a.getOption(UCCore.OPTION_NEW_UCM_ZIP_FILE));
            return;
        }
        if (UCCore.BUSINESS_INIT_BY_OLD_CORE_DEX_DIR.equals(baseSetupTask.getInitType())) {
            ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) BusinessWrapper.decompressAndODex(UCCore.OPTION_CONTEXT, this.a.getContext()).setup("o_flag_odex_done", (Object) Boolean.TRUE)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, this.a.getOption(UCCore.OPTION_NEW_UCM_ZIP_FILE))).setup((String) UCCore.OPTION_ZIP_FILE_TYPE, this.a.getOption(UCCore.OPTION_NEW_UCM_ZIP_TYPE))).setup((String) UCCore.OPTION_DECOMPRESS_ROOT_DIR, this.a.getOption(UCCore.OPTION_BUSINESS_DECOMPRESS_ROOT_PATH))).setup((String) UCCore.OPTION_DELETE_AFTER_EXTRACT, (Object) Boolean.FALSE)).setup((String) UCCore.OPTION_PROVIDED_KEYS, this.a.getOption(UCCore.OPTION_PROVIDED_KEYS))).setup((String) UCCore.OPTION_DECOMPRESS_CALLBACK, this.a.getOption(UCCore.OPTION_DECOMPRESS_CALLBACK))).setup((String) UCCore.OPTION_DECOMPRESS_AND_ODEX_CALLBACK, this.a.getOption(UCCore.OPTION_DECOMPRESS_AND_ODEX_CALLBACK))).setup((String) UCCore.OPTION_VERIFY_POLICY, this.a.getOption(UCCore.OPTION_VERIFY_POLICY))).start(1000);
        }
    }
}
