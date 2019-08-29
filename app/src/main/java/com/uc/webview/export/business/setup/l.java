package com.uc.webview.export.business.setup;

import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import java.util.HashMap;

/* compiled from: ProGuard */
final class l extends HashMap<String, Pair<ValueCallback<BaseSetupTask>, ValueCallback<BaseSetupTask>>> {
    final /* synthetic */ a a;

    l(a aVar) {
        this.a = aVar;
        put(LogCategory.CATEGORY_EXCEPTION, new Pair(null, this.a.i));
        put("die_delegate", new Pair(null, this.a.j));
        put(UCCore.EVENT_INIT_CORE_SUCCESS, new Pair(null, this.a.h));
        put("setup", new Pair(null, this.a.k));
    }
}
