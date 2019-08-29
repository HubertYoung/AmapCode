package com.alipay.sdk.auth;

import android.webkit.SslErrorHandler;
import com.alipay.sdk.widget.d;

final class e implements Runnable {
    final /* synthetic */ SslErrorHandler a;
    final /* synthetic */ b b;

    e(b bVar, SslErrorHandler sslErrorHandler) {
        this.b = bVar;
        this.a = sslErrorHandler;
    }

    public final void run() {
        d.a(AuthActivity.this, "安全警告", "由于您的设备缺少根证书，将无法校验该访问站点的安全性，可能存在风险，请选择是否继续？", "继续", new f(this), "退出", new g(this));
    }
}
