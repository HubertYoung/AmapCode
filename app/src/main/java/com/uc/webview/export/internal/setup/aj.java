package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.utility.Log;
import java.util.ArrayList;

/* compiled from: ProGuard */
final class aj implements ValueCallback<t> {
    final /* synthetic */ af a;

    aj(af afVar) {
        this.a = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        if (SDKFactory.B != null) {
            SDKFactory.B.onReceiveValue(tVar.getException());
        }
        if (tVar.getException() != null) {
            this.a.g = tVar.getException();
            this.a.h = tVar;
        }
        Integer num = (Integer) this.a.mOptions.get(UCCore.OPTION_DELETE_CORE_POLICY);
        if (num != null && num.intValue() != 0 && (tVar instanceof bl) && ((tVar.getException().errCode() == 1008 && (num.intValue() & 1) != 0) || ((tVar.getException().errCode() == 1011 && (num.intValue() & 8) != 0) || ((tVar.getException().errCode() == 3007 && (num.intValue() & 2) != 0) || ((tVar.getException().errCode() == 3005 && (num.intValue() & 16) != 0) || ((tVar.getException().errCode() == 4005 && (num.intValue() & 4) != 0) || (num.intValue() & 32) != 0)))))) {
            if (this.a.l == null) {
                this.a.l = new ArrayList();
            }
            this.a.l.add((bl) tVar);
        }
        StringBuilder sb = new StringBuilder("mExceptionCB mExceptionTasks: ");
        sb.append(af.b);
        Log.d("SdkSetupTask", sb.toString());
        if (af.b.size() > 0) {
            ((UCSetupTask) af.b.pop()).start();
        } else if (this.a.c != null) {
            ((t) ((t) ((t) this.a.c.invoke(10001, this.a)).onEvent((String) "success", this.a.m)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, this.a.n)).start();
            this.a.c = null;
        } else {
            this.a.setException(tVar.getException());
        }
    }
}
