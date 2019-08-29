package com.autonavi.bundle.carlogo.ajx;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.deviceid.DeviceTokenClient;
import com.alipay.sdk.util.j;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("user")
public class ModuleUser extends AbstractModule {
    private fhf mAlipayProxy = new fhf();

    public static class a implements ans<fhh> {
        WeakReference<ModuleUser> a;
        JsFunctionCallback b;

        public final /* synthetic */ void a(Object obj) {
            fhh fhh = (fhh) obj;
            if (this.a != null && this.a.get() != null && this.b != null) {
                this.b.callback(ModuleUser.getPayResultCallbackString(fhh));
            }
        }

        a(ModuleUser moduleUser, JsFunctionCallback jsFunctionCallback) {
            this.a = new WeakReference<>(moduleUser);
            this.b = jsFunctionCallback;
        }
    }

    static class b implements defpackage.fhf.a {
        WeakReference<ModuleUser> a;
        JsFunctionCallback b;

        b(ModuleUser moduleUser, JsFunctionCallback jsFunctionCallback) {
            this.a = new WeakReference<>(moduleUser);
            this.b = jsFunctionCallback;
        }

        public final void a(String str, int i) {
            if (this.a != null && this.a.get() != null && this.b != null) {
                this.b.callback(ModuleUser.getDeviceTokenCallbackString(str, i));
            }
        }
    }

    public ModuleUser(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("pay")
    public void pay(String str, JsFunctionCallback jsFunctionCallback) {
        fhf fhf = this.mAlipayProxy;
        Activity activity = DoNotUseTool.getActivity();
        a aVar = new a(this, jsFunctionCallback);
        new Object[1][0] = str;
        if (!TextUtils.isEmpty(str) && activity != null) {
            ahm.a(new d(fhf, str, activity, aVar));
        }
    }

    @AjxMethod("getAlipaySecureToken")
    public void getAlipaySecureToken(JsFunctionCallback jsFunctionCallback) {
        DeviceTokenClient.getInstance(AMapPageUtil.getAppContext()).initToken("amap", "cDqkAkHfdJAYWbFs", new c(this.mAlipayProxy, new b(this, jsFunctionCallback)));
    }

    /* access modifiers changed from: private */
    public static String getPayResultCallbackString(fhh fhh) {
        if (fhh == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("memo", fhh.c);
            jSONObject.put("result", fhh.b);
            jSONObject.put(j.a, fhh.a);
        } catch (JSONException unused) {
            fhf.b();
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    public static String getDeviceTokenCallbackString(String str, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("token", str);
            jSONObject.put("error_code", i);
        } catch (JSONException unused) {
            fhf.b();
        }
        return jSONObject.toString();
    }

    @AjxMethod("doDownLoadCarLogo")
    public void doDownLoadCarLogo(String str, String str2, String str3, String str4) {
        ((atf) defpackage.esb.a.a.a(atf.class)).a(str, str2, str3, str4);
    }
}
