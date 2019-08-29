package defpackage;

import com.autonavi.bundle.account.api.IThirdAuth;
import com.autonavi.bundle.account.api.IThirdAuth.IBaichuanSDKWebViewApi;
import com.autonavi.bundle.account.api.IThirdAuth.a;
import com.autonavi.bundle.account.impl.BaichuanSDKWebViewApiImpl;
import com.autonavi.common.Callback;

/* renamed from: aop reason: default package */
/* compiled from: ThirdAuthImpl */
public final class aop implements IThirdAuth {
    public final a a() {
        return aoj.d();
    }

    public final IBaichuanSDKWebViewApi b() {
        return BaichuanSDKWebViewApiImpl.b();
    }

    public final void a(Callback<String> callback) {
        new aoo().a(callback);
    }
}
