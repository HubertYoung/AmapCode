package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class ak implements ValueCallback<t> {
    final /* synthetic */ af a;

    ak(af afVar) {
        this.a = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        String str;
        t tVar = (t) obj;
        if (this.a.k) {
            StringBuilder sb = new StringBuilder("ThickSetupTask_");
            sb.append((String) tVar.getOption(UCCore.OPTION_SO_FILE_PATH));
            str = sb.toString();
        } else {
            str = "";
        }
        if (str == null) {
            str = (String) tVar.getOption(UCCore.OPTION_DEX_FILE_PATH);
        }
        if (str == null) {
            str = (String) tVar.getOption(UCCore.OPTION_UCM_ZIP_FILE);
        }
        if (str == null) {
            str = (String) tVar.getOption(UCCore.OPTION_UCM_LIB_DIR);
        }
        if (str == null) {
            str = (String) tVar.getOption(UCCore.OPTION_UCM_KRL_DIR);
        }
        if (str == null) {
            str = (String) tVar.getOption(UCCore.OPTION_UCM_CFG_FILE);
        }
        tVar.setException(new UCSetupException(4005, String.format("Multi crash detected in [%s].", new Object[]{str})));
        tVar.onEvent((String) LocationParams.PARA_COMMON_DIE, (ValueCallback<CALLBACK_TYPE>) null);
    }
}
