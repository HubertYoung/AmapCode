package com.alipay.mobile.android.verify.sdk;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.sdk.a.a;
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback;
import com.alipay.mobile.android.verify.sdk.interfaces.IService;
import com.alipay.mobile.security.zim.api.ZIMFacade;

/* compiled from: ServiceImpl */
public class c implements IService {
    private boolean a = false;
    /* access modifiers changed from: private */
    public b b;

    c() {
    }

    public String getMetaInfo(Context context) {
        return ZIMFacade.getMetaInfos(context);
    }

    public synchronized void startService(Activity activity, String str, ICallback iCallback) {
        if (!this.a) {
            new a().a();
            a.b("startZMSDK");
            this.a = true;
            f.a(new d(this, activity, str, iCallback));
        }
    }

    public void startService(Activity activity, JSONObject jSONObject, ICallback iCallback) {
        if (!this.a && jSONObject != null && !TextUtils.isEmpty(jSONObject.getString("url"))) {
            new a().a();
            a.b("startZMSDK");
            this.a = true;
            f.a(new e(this, activity, jSONObject, iCallback));
        }
    }
}
