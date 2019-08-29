package com.amap.bundle.schoolbus.router;

import android.net.Uri;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;

public class SchoolbusRouter$2 implements Callback<adj> {
    final /* synthetic */ Uri a;
    final /* synthetic */ adr b;

    public SchoolbusRouter$2(adr adr, Uri uri) {
        this.b = adr;
        this.a = uri;
    }

    public void callback(adj adj) {
        adr.c(this.b);
        if (!this.b.a) {
            int i = adj.c;
            int i2 = adj.a;
            if (i2 == 1) {
                adr.a(this.b, i);
            } else if (i2 == 14) {
                ((IAccountService) a.a.a(IAccountService.class)).a(adr.a(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                    }
                });
            } else {
                adr.a(this.b, 100);
            }
        }
    }

    public void error(Throwable th, boolean z) {
        adr.c(this.b);
        ToastHelper.showLongToast("请检查网络后重试");
    }
}
