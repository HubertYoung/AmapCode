package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class ao implements ValueCallback<t> {
    final /* synthetic */ af a;

    ao(af afVar) {
        this.a = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        Log.d("SdkSetupTask", "mShareCoreCB ".concat(String.valueOf((t) obj)));
        if (CDParamKeys.CD_VALUE_LOAD_POLICY_SHARE_CORE.equals(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_LOAD_POLICY))) {
            ((t) ((t) ((t) ((t) ((t) this.a.b().invoke(10001, UCSetupTask.getRoot())).onEvent((String) FunctionSupportConfiger.SWITCH_TAG, this.a.q)).onEvent((String) "success", (ValueCallback<CALLBACK_TYPE>) new aq<CALLBACK_TYPE>(this))).onEvent((String) LogCategory.CATEGORY_EXCEPTION, (ValueCallback<CALLBACK_TYPE>) new ap<CALLBACK_TYPE>(this))).onEvent(be.c, (ValueCallback<CALLBACK_TYPE>) null)).start();
        }
    }
}
