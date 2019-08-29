package com.amap.bundle.pay.ajx;

import android.content.Intent;
import android.net.Uri;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import operation.pay.AliSignTools;
import operation.pay.AliSignTools.AliPayUnbindWrapper;
import operation.pay.AliSignTools.AlipayConfWrapper;

@AjxModule("common_alipayFreepay")
public final class ModuleAlipayFreepay extends AbstractModule {
    public ModuleAlipayFreepay(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("bind")
    public final void bind(final JsFunctionCallback jsFunctionCallback) {
        fhg.a().a(new ans<String>() {
            public final /* synthetic */ void a(Object obj) {
                jsFunctionCallback.callback((String) obj);
            }
        }, DoNotUseTool.getActivity());
    }

    @AjxMethod("unbind")
    public final void unbind(final JsFunctionCallback jsFunctionCallback) {
        fhg a = fhg.a();
        AnonymousClass2 r1 = new ans<String>() {
            public final /* synthetic */ void a(Object obj) {
                jsFunctionCallback.callback((String) obj);
            }
        };
        a.a.a(DoNotUseTool.getActivity());
        a.a.a = r1;
        AliSignTools aliSignTools = a.a;
        AosPostRequest b = aax.b(new AliPayUnbindWrapper());
        yq.a();
        yq.a((AosRequest) b, (AosResponseCallback<T>) aliSignTools.c);
    }

    @AjxMethod("signZhiMa")
    public final void signZhiMa(String str, final JsFunctionCallback jsFunctionCallback) {
        fhg a = fhg.a();
        AnonymousClass3 r1 = new ans<String>() {
            public final /* synthetic */ void a(Object obj) {
                jsFunctionCallback.callback((String) obj);
            }
        };
        a.a.a(DoNotUseTool.getActivity());
        a.a.a = r1;
        AliSignTools aliSignTools = a.a;
        AliSignTools.a((String) "AliSignTools. signZhiMa. role: %s", str);
        AosGetRequest a2 = aax.a(new AlipayConfWrapper());
        a2.addHeader("Cookie", abj.a().b());
        aliSignTools.d = str;
        yq.a();
        yq.a((AosRequest) a2, (AosResponseCallback<T>) aliSignTools.f);
    }

    @AjxMethod(invokeMode = "sync", value = "isSendingZhiMaCheck")
    public final String isSendingZhiMaCheck() {
        return fhg.a().a.g ? "1" : "0";
    }

    @AjxMethod(invokeMode = "sync", value = "installedAlipay")
    public final String installedAlipay() {
        return new Intent("android.intent.action.VIEW", Uri.parse("alipays://platformapi/startApp")).resolveActivity(getNativeContext().getPackageManager()) != null ? "1" : "0";
    }

    @AjxMethod("openAlipayAuthManagePage")
    public final void openAlipayAuthManagePage() {
        try {
            getNativeContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("alipays://platformapi/startapp?appId=20000055&bizTab=appAuth&launchType=manage")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
